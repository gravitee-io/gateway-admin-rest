/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.management.rest.resource;

import io.gravitee.common.http.MediaType;
import io.gravitee.management.model.api.header.ApiHeaderEntity;
import io.gravitee.management.model.api.header.NewApiHeaderEntity;
import io.gravitee.management.model.permissions.RolePermission;
import io.gravitee.management.model.permissions.RolePermissionAction;
import io.gravitee.management.rest.security.Permission;
import io.gravitee.management.rest.security.Permissions;
import io.gravitee.management.service.ApiHeaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import java.util.List;

import static io.gravitee.common.http.MediaType.APPLICATION_JSON;

/**
 * @author Nicolas GERAUD (nicolas.geraud at graviteesource.com)
 * @author GraviteeSource Team
 */
@Api(tags = {"Configuration"})
public class ApiHeadersResource extends AbstractResource {

    @Context
    private ResourceContext resourceContext;
    @Autowired
    private ApiHeaderService apiHeaderService;

    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "List API headers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of API headers", response = ApiHeaderEntity.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @Permissions({
            @Permission(value = RolePermission.PORTAL_API_HEADER, acls = RolePermissionAction.READ)
    })
    public List<ApiHeaderEntity> get() {
        return apiHeaderService.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create an API header",
            notes = "User must have the PORTAL_API_HEADER[CREATE] permission to use this service")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API header successfully created", response = ApiHeaderEntity.class),
            @ApiResponse(code = 500, message = "Internal server error")})
    @Permissions({
            @Permission(value = RolePermission.PORTAL_API_HEADER, acls = RolePermissionAction.CREATE)
    })
    public ApiHeaderEntity create(@Valid @NotNull final NewApiHeaderEntity newApiHeaderEntity) {
        return apiHeaderService.create(newApiHeaderEntity);
    }

    @Path("{id}")
    public ApiHeaderResource getApiHeaderResource() {
        return resourceContext.getResource(ApiHeaderResource.class);
    }
}
