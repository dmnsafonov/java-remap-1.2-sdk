package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PurchaseReturnDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity purchaseReturn = new PurchaseReturnDocumentEntity();
        purchaseReturn.setName("purchasereturn_" + randomString(3) + "_" + new Date().getTime());
        purchaseReturn.setDescription(randomString());
        purchaseReturn.setVatEnabled(true);
        purchaseReturn.setVatIncluded(true);
        purchaseReturn.setMoment(LocalDateTime.now());
        OrganizationEntity organization = simpleEntityFactory.getOwnOrganization();
        purchaseReturn.setOrganization(organization);
        CounterpartyEntity agent = simpleEntityFactory.createSimpleCounterparty();
        purchaseReturn.setAgent(agent);
        StoreEntity mainStore = simpleEntityFactory.getMainStore();
        purchaseReturn.setStore(mainStore);

        SupplyDocumentEntity supply = new SupplyDocumentEntity();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setOrganization(organization);
        supply.setAgent(agent);
        supply.setStore(mainStore);

        api.entity().supply().post(supply);
        purchaseReturn.setSupply(supply);

        api.entity().purchasereturn().post(purchaseReturn);

        ListEntity<PurchaseReturnDocumentEntity> updatedEntitiesList = api.entity().purchasereturn().get(filterEq("name", purchaseReturn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PurchaseReturnDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(purchaseReturn.getName(), retrievedEntity.getName());
        assertEquals(purchaseReturn.getDescription(), retrievedEntity.getDescription());
        assertEquals(purchaseReturn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(purchaseReturn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(purchaseReturn.getMoment(), retrievedEntity.getMoment());
        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(purchaseReturn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(purchaseReturn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(purchaseReturn.getSupply().getMeta().getHref(), retrievedEntity.getSupply().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().purchasereturn().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity purchaseReturn = api.entity().purchasereturn().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseReturn.getName());
        assertTrue(purchaseReturn.getVatEnabled());
        assertTrue(purchaseReturn.getVatIncluded());
        assertEquals(Long.valueOf(0), purchaseReturn.getSum());
        assertFalse(purchaseReturn.getShared());
        assertTrue(purchaseReturn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseReturn.getMoment()) < 1000);

        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), simpleEntityFactory.getOwnOrganization().getMeta().getHref());
        assertEquals(purchaseReturn.getStore().getMeta().getHref(), simpleEntityFactory.getMainStore().getMeta().getHref());
        assertEquals(purchaseReturn.getGroup().getMeta().getHref(), simpleEntityFactory.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newBySupplyTest() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = simpleEntityFactory.createSimpleSupply();

        PurchaseReturnDocumentEntity purchaseReturn = api.entity().purchasereturn().newDocument("supply", supply);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseReturn.getName());
        assertEquals(supply.getVatEnabled(), purchaseReturn.getVatEnabled());
        assertEquals(supply.getVatIncluded(), purchaseReturn.getVatIncluded());
        assertEquals(supply.getPayedSum(), purchaseReturn.getPayedSum());
        assertEquals(supply.getSum(), purchaseReturn.getSum());
        assertFalse(purchaseReturn.getShared());
        assertTrue(purchaseReturn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseReturn.getMoment()) < 1000);
        assertEquals(supply.getMeta().getHref(), purchaseReturn.getSupply().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), purchaseReturn.getAgent().getMeta().getHref());
        assertEquals(supply.getStore().getMeta().getHref(), purchaseReturn.getStore().getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), purchaseReturn.getGroup().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), purchaseReturn.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        PurchaseReturnDocumentEntity originalPurchaseReturn = (PurchaseReturnDocumentEntity) originalEntity;
        PurchaseReturnDocumentEntity retrievedPurchaseReturn = (PurchaseReturnDocumentEntity) retrievedEntity;

        assertEquals(originalPurchaseReturn.getName(), retrievedPurchaseReturn.getName());
        assertEquals(originalPurchaseReturn.getDescription(), retrievedPurchaseReturn.getDescription());
        assertEquals(originalPurchaseReturn.getOrganization().getMeta().getHref(), retrievedPurchaseReturn.getOrganization().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getAgent().getMeta().getHref(), retrievedPurchaseReturn.getAgent().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getStore().getMeta().getHref(), retrievedPurchaseReturn.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        PurchaseReturnDocumentEntity originalPurchaseReturn = (PurchaseReturnDocumentEntity) originalEntity;
        PurchaseReturnDocumentEntity updatedPurchaseReturn = (PurchaseReturnDocumentEntity) updatedEntity;

        assertNotEquals(originalPurchaseReturn.getName(), updatedPurchaseReturn.getName());
        assertEquals(changedField, updatedPurchaseReturn.getName());
        assertEquals(originalPurchaseReturn.getDescription(), updatedPurchaseReturn.getDescription());
        assertEquals(originalPurchaseReturn.getOrganization().getMeta().getHref(), updatedPurchaseReturn.getOrganization().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getAgent().getMeta().getHref(), updatedPurchaseReturn.getAgent().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getStore().getMeta().getHref(), updatedPurchaseReturn.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().purchasereturn();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return PurchaseReturnDocumentEntity.class;
    }
}