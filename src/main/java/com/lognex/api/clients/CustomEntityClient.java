package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.CustomEntity;
import com.lognex.api.entities.CustomEntityElement;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class CustomEntityClient
        extends EntityClientBase
        implements
        PostEndpoint<CustomEntity>,
        PutByIdEndpoint<CustomEntity>,
        DeleteByIdEndpoint {

    public CustomEntityClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/customentity/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CustomEntity.class;
    }

    @ApiEndpoint
    public CustomEntityElement createCustomEntityElement(String customEntityMetadataId, CustomEntityElement customEntityElement) throws IOException, ApiClientException {
         CustomEntityElement responseEntity = HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId).
                body(customEntityElement).
                post(CustomEntityElement.class);

         customEntityElement.set(responseEntity);
         customEntityElement.setCustomDictionaryId(customEntityMetadataId);
         return customEntityElement;
    }

    @ApiEndpoint
    public CustomEntityElement getCustomEntityElement(String customEntityMetadataId, String customEntityId) throws IOException, ApiClientException {
        CustomEntityElement customEntityElement = HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId + "/" + customEntityId).
                get(CustomEntityElement.class);

        customEntityElement.setCustomDictionaryId(customEntityMetadataId);
        return customEntityElement;
    }

    @ApiEndpoint
    public ListEntity<CustomEntityElement> getCustomEntityElements(String customEntityMetadataId) throws IOException, ApiClientException {
        ListEntity<CustomEntityElement> customEntityElements = HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId).
                list(CustomEntityElement.class);

        List<CustomEntityElement> elementsList = customEntityElements.getRows();
        if (elementsList != null) {
            elementsList = elementsList.stream().peek(c -> c.setCustomDictionaryId(customEntityMetadataId)).collect(Collectors.toList());
            customEntityElements.setRows(elementsList);
        }
        return customEntityElements;
    }

    @ApiEndpoint
    public ListEntity<CustomEntityElement> getCustomEntityElements(CustomEntity customEntity) throws IOException, ApiClientException {
        return getCustomEntityElements(customEntity.getId());
    }

    @ApiEndpoint
    public CustomEntityElement updateCustomEntityElement(String customEntityMetadataId, String customEntityId, CustomEntityElement updatedEntity) throws IOException, ApiClientException {
        CustomEntityElement responseEntity = HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId + "/" + customEntityId).
                body(updatedEntity).
                put(CustomEntityElement.class);

        updatedEntity.set(responseEntity);
        updatedEntity.setCustomDictionaryId(customEntityMetadataId);
        return updatedEntity;
    }

    @ApiEndpoint
    public CustomEntityElement updateCustomEntityElement(String customEntityMetadataId, CustomEntityElement customEntityElement) throws IOException, ApiClientException {
        return updateCustomEntityElement(customEntityMetadataId, customEntityElement.getId(), customEntityElement);
    }

    @ApiEndpoint
    public void deleteCustomEntityElement(String customEntityMetadataId, String customEntityElementId) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + customEntityMetadataId + "/" + customEntityElementId).
                delete();
    }

    @ApiEndpoint
    public void deleteCustomEntityElement(String customEntityMetadataId, CustomEntityElement customEntityElement) throws IOException, ApiClientException {
        deleteCustomEntityElement(customEntityMetadataId, customEntityElement.getId());
    }
}
