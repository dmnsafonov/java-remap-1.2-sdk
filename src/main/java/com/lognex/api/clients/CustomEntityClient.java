package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.CustomEntity;
import com.lognex.api.entities.CustomEntityElement;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CustomEntityClient
        extends ApiClient
        implements
        PostEndpoint<CustomEntity>,
        PutByIdEndpoint<CustomEntity>,
        DeleteByIdEndpoint {

    public CustomEntityClient(LognexApi api) {
        super(api, "/entity/customentity/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CustomEntity.class;
    }

    @ApiEndpoint
    public CustomEntityElement postCustomEntityElement(String customEntityMetadataId, CustomEntityElement customEntityElement) throws IOException, LognexApiException {
         CustomEntityElement responseEntity = HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId).
                body(customEntityElement).
                post(CustomEntityElement.class);

         customEntityElement.set(responseEntity);
         return customEntityElement;
    }

    @ApiEndpoint
    public CustomEntityElement getCustomEntityElement(String customEntityMetadataId, String customEntityId) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId + "/" + customEntityId).
                get(CustomEntityElement.class);
    }

    @ApiEndpoint
    public ListEntity<CustomEntityElement> getCustomEntityElements(String customEntityMetadataId) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId).
                list(CustomEntityElement.class);
    }

    @ApiEndpoint
    public ListEntity<CustomEntityElement> getCustomEntityElements(CustomEntity customEntity) throws IOException, LognexApiException {
        return getCustomEntityElements(customEntity.getId());
    }

    @ApiEndpoint
    public CustomEntityElement putCustomEntityElement(String customEntityMetadataId, String customEntityId, CustomEntityElement updatedEntity) throws IOException, LognexApiException {
        CustomEntityElement responseEntity = HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId + "/" + customEntityId).
                body(updatedEntity).
                put(CustomEntityElement.class);

        updatedEntity.set(responseEntity);
        return updatedEntity;
    }

    @ApiEndpoint
    public CustomEntityElement putCustomEntityElement(String customEntityMetadataId, CustomEntityElement customEntityElement) throws IOException, LognexApiException {
        return putCustomEntityElement(customEntityMetadataId, customEntityElement.getId(), customEntityElement);
    }

    @ApiEndpoint
    public void deleteCustomEntityElement(String customEntityMetadataId, String customEntityElementId) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId + "/" + customEntityElementId).
                delete();
    }

    @ApiEndpoint
    public void deleteCustomEntityElement(String customEntityMetadataId, CustomEntityElement customEntityElement) throws IOException, LognexApiException {
        deleteCustomEntityElement(customEntityMetadataId, customEntityElement.getId());
    }
}