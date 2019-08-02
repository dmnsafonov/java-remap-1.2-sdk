package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class RetailSalesReturnDocumentEntityTest extends EntityGetUpdateDeleteTest {
    @Ignore
    @Test
    public void createTest() throws IOException, LognexApiException {
        RetailSalesReturnEntity retailSalesReturn = new RetailSalesReturnEntity();
        retailSalesReturn.setName("retailsalesreturn_" + randomString(3) + "_" + new Date().getTime());
        retailSalesReturn.setDescription(randomString());
        retailSalesReturn.setVatEnabled(true);
        retailSalesReturn.setVatIncluded(true);
        retailSalesReturn.setMoment(LocalDateTime.now());

        retailSalesReturn.setOrganization(simpleEntityFactory.getOwnOrganization());
        retailSalesReturn.setAgent(simpleEntityFactory.createSimpleCounterparty());
        retailSalesReturn.setStore(simpleEntityFactory.getMainStore());

        api.entity().retailsalesreturn().post(retailSalesReturn);

        ListEntity<RetailSalesReturnEntity> updatedEntitiesList = api.entity().retailsalesreturn().get(filterEq("name", retailSalesReturn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailSalesReturnEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(retailSalesReturn.getName(), retrievedEntity.getName());
        assertEquals(retailSalesReturn.getDescription(), retrievedEntity.getDescription());
        assertEquals(retailSalesReturn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(retailSalesReturn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(retailSalesReturn.getMoment(), retrievedEntity.getMoment());
        assertEquals(retailSalesReturn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(retailSalesReturn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(retailSalesReturn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Ignore
    @Test
    @Override
    public void putTest() throws IOException, LognexApiException {
    }

    @Ignore
    @Test
    @Override
    public void deleteTest() throws IOException, LognexApiException {
    }

    @Ignore
    @Test
    @Override
    public void getTest() throws IOException, LognexApiException {
    }

    @Ignore
    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().retailsalesreturn().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        SalesReturnDocumentEntity originalSalesReturn = (SalesReturnDocumentEntity) originalEntity;
        SalesReturnDocumentEntity retrievedSalesReturn = (SalesReturnDocumentEntity) retrievedEntity;

        assertEquals(originalSalesReturn.getName(), retrievedSalesReturn.getName());
        assertEquals(originalSalesReturn.getDescription(), retrievedSalesReturn.getDescription());
        assertEquals(originalSalesReturn.getOrganization().getMeta().getHref(), retrievedSalesReturn.getOrganization().getMeta().getHref());
        assertEquals(originalSalesReturn.getAgent().getMeta().getHref(), retrievedSalesReturn.getAgent().getMeta().getHref());
        assertEquals(originalSalesReturn.getStore().getMeta().getHref(), retrievedSalesReturn.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        SalesReturnDocumentEntity originalSalesReturn = (SalesReturnDocumentEntity) originalEntity;
        SalesReturnDocumentEntity updatedSalesReturn = (SalesReturnDocumentEntity) updatedEntity;

        assertNotEquals(originalSalesReturn.getName(), updatedSalesReturn.getName());
        assertEquals(changedField, updatedSalesReturn.getName());
        assertEquals(originalSalesReturn.getDescription(), updatedSalesReturn.getDescription());
        assertEquals(originalSalesReturn.getOrganization().getMeta().getHref(), updatedSalesReturn.getOrganization().getMeta().getHref());
        assertEquals(originalSalesReturn.getAgent().getMeta().getHref(), updatedSalesReturn.getAgent().getMeta().getHref());
        assertEquals(originalSalesReturn.getStore().getMeta().getHref(), updatedSalesReturn.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().salesreturn();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return SalesReturnDocumentEntity.class;
    }
}