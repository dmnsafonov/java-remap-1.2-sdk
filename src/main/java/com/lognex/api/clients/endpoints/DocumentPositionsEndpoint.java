package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.documents.DocumentEntity;
import com.lognex.api.entities.documents.DocumentPosition;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface DocumentPositionsEndpoint extends Endpoint {
    @ApiEndpoint
    default List<DocumentPosition> postPositions(String documentId, List<DocumentPosition> updatedEntities) throws IOException, LognexApiException {
        List<DocumentPosition> responseEntity = HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/").
                body(updatedEntities).
                postList(DocumentPosition.class);

        for (int i = 0; i < responseEntity.size(); i++) {
            updatedEntities.set(i, responseEntity.get(i));
        }
        return updatedEntities;
    }

    @ApiEndpoint
    default List<DocumentPosition> postPositions(DocumentEntity document, List<DocumentPosition> updatedEntities) throws IOException, LognexApiException {
        return postPositions(document.getId(), updatedEntities);
    }

    @ApiEndpoint
    default DocumentPosition postPosition(String documentId, DocumentPosition updatedEntity) throws IOException, LognexApiException {
        List<DocumentPosition> positionList = new ArrayList<>(Collections.singletonList(updatedEntity));
        List<DocumentPosition> newPosition = postPositions(documentId, positionList);

        updatedEntity.set(newPosition.get(0));
        return updatedEntity;
    }

    @ApiEndpoint
    default DocumentPosition postPosition(DocumentEntity document, DocumentPosition updatedEntity) throws IOException, LognexApiException {
        return postPosition(document.getId(), updatedEntity);
    }

    @ApiEndpoint
    default ListEntity<DocumentPosition> getPositions(String documentId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/positions").
                apiParams(params).
                list(DocumentPosition.class);
    }

    @ApiEndpoint
    default ListEntity<DocumentPosition> getPositions(DocumentEntity document, ApiParam... params) throws IOException, LognexApiException {
        return getPositions(document.getId(), params);
    }

    @ApiEndpoint
    default DocumentPosition getPosition(String documentId, String positionId, ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                apiParams(params).
                get(DocumentPosition.class);
    }

    @ApiEndpoint
    default DocumentPosition getPosition(DocumentEntity document, String positionId, ApiParam... params) throws IOException, LognexApiException {
        return getPosition(document.getId(), positionId, params);
    }

    @ApiEndpoint
    default void putPosition(String documentId, String positionId, DocumentPosition updatedEntity) throws IOException, LognexApiException {
        DocumentPosition responseEntity = HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                body(updatedEntity).
                put(DocumentPosition.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    default void putPosition(DocumentEntity document, String positionId, DocumentPosition updatedEntity) throws IOException, LognexApiException {
        putPosition(document.getId(), positionId, updatedEntity);
    }

    @ApiEndpoint
    default void putPosition(DocumentEntity document, DocumentPosition position, DocumentPosition updatedEntity) throws IOException, LognexApiException {
        putPosition(document, position.getId(), updatedEntity);
    }

    @ApiEndpoint
    default void putPosition(DocumentEntity document, DocumentPosition position) throws IOException, LognexApiException {
        putPosition(document, position, position);
    }

    @ApiEndpoint
    default void delete(String documentId, String positionId) throws IOException, LognexApiException {
        HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                delete();
    }

    @ApiEndpoint
    default void delete(DocumentEntity document, String positionId) throws IOException, LognexApiException {
        delete(document.getId(), positionId);
    }

    @ApiEndpoint
    default void delete(DocumentEntity document, DocumentPosition position) throws IOException, LognexApiException {
        delete(document, position.getId());
    }
}