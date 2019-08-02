package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.EnterDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentEnterClient
        extends ApiClient
        implements
        GetListEndpoint<EnterDocumentEntity>,
        PostEndpoint<EnterDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<EnterDocumentEntity>,
        PutByIdEndpoint<EnterDocumentEntity>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentEnterClient(LognexApi api) {
        super(api, "/entity/enter/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return EnterDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}