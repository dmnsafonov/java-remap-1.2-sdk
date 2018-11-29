package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.DocumentMetadataEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.MetadataAttributeEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.LossDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentLossClient
        extends ApiClient
        implements
        GetListEndpoint<LossDocumentEntity>,
        PostEndpoint<LossDocumentEntity>,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint {

    public DocumentLossClient(LognexApi api) {
        super(api, "/entity/loss/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return LossDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
