package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.PurchaseOrder;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class PurchaseOrderClient
        extends EntityClientBase
        implements
        GetListEndpoint<PurchaseOrder>,
        PostEndpoint<PurchaseOrder>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<PurchaseOrder>,
        GetByIdEndpoint<PurchaseOrder>,
        PutByIdEndpoint<PurchaseOrder>,
        DocumentPositionsEndpoint,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public PurchaseOrderClient(ApiClient api) {
        super(api, "/entity/purchaseorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PurchaseOrder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}