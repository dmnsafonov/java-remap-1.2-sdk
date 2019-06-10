package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProcessingOrderDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity processingOrder = new ProcessingOrderDocumentEntity();
        processingOrder.setName("processingorder_" + randomString(3) + "_" + new Date().getTime());
        processingOrder.setDescription(randomString());
        processingOrder.setMoment(LocalDateTime.now());
        processingOrder.setOrganization(simpleEntityFactory.getOwnOrganization());
        processingOrder.setStore(simpleEntityFactory.getMainStore());

        ProcessingPlanDocumentEntity processingPlan = new ProcessingPlanDocumentEntity();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = simpleEntityFactory.createSimpleProduct();
        ProcessingPlanDocumentEntity.PlanItem materialItem = new ProcessingPlanDocumentEntity.PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProductEntity product = simpleEntityFactory.createSimpleProduct();
        ProcessingPlanDocumentEntity.PlanItem productItem = new ProcessingPlanDocumentEntity.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(processingPlan);
        processingOrder.setProcessingPlan(processingPlan);

        processingOrder.setPositions(new ListEntity<>());
        processingOrder.getPositions().setRows(new ArrayList<>());
        DocumentPosition position = new DocumentPosition();
        position.setQuantity(3.1234);
        position.setAssortment(material);
        processingOrder.getPositions().getRows().add(position);

        api.entity().processingorder().post(processingOrder);

        ListEntity<ProcessingOrderDocumentEntity> updatedEntitiesList = api.entity().processingorder().
                get(limit(50), filterEq("name", processingOrder.getName()), expand("positions"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProcessingOrderDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(processingOrder.getName(), retrievedEntity.getName());
        assertEquals(processingOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(processingOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(processingOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(processingOrder.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(processingOrder.getProcessingPlan().getMeta().getHref(), retrievedEntity.getProcessingPlan().getMeta().getHref());
        assertEquals(position.getQuantity(), retrievedEntity.getPositions().getRows().get(0).getQuantity());
        ProductEntity retrievedProduct = (ProductEntity) retrievedEntity.getPositions().getRows().get(0).getAssortment();
        assertEquals(material.getMeta().getHref(), retrievedProduct.getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity processingOrder = simpleEntityFactory.createSimpleProcessingOrder();

        ProcessingOrderDocumentEntity retrievedEntity = api.entity().processingorder().get(processingOrder.getId());
        getAsserts(processingOrder, retrievedEntity);

        retrievedEntity = api.entity().processingorder().get(processingOrder);
        getAsserts(processingOrder, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity processingOrder = simpleEntityFactory.createSimpleProcessingOrder();

        ProcessingOrderDocumentEntity retrievedOriginalEntity = api.entity().processingorder().get(processingOrder.getId());
        String name = "processingorder_" + randomString(3) + "_" + new Date().getTime();
        processingOrder.setName(name);
        api.entity().processingorder().put(processingOrder.getId(), processingOrder);
        putAsserts(processingOrder, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(processingOrder);

        name = "processingorder_" + randomString(3) + "_" + new Date().getTime();
        processingOrder.setName(name);
        api.entity().processingorder().put(processingOrder);
        putAsserts(processingOrder, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity processingOrder = simpleEntityFactory.createSimpleProcessingOrder();

        ListEntity<ProcessingOrderDocumentEntity> entitiesList = api.entity().processingorder().get(filterEq("name", processingOrder.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().processingorder().delete(processingOrder.getId());

        entitiesList = api.entity().processingorder().get(filterEq("name", processingOrder.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity processingOrder = simpleEntityFactory.createSimpleProcessingOrder();

        ListEntity<ProcessingOrderDocumentEntity> entitiesList = api.entity().processingorder().get(filterEq("name", processingOrder.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().processingorder().delete(processingOrder);

        entitiesList = api.entity().processingorder().get(filterEq("name", processingOrder.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().processingorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newByProcessingPlanTest() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity processingPlan = new ProcessingPlanDocumentEntity();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = simpleEntityFactory.createSimpleProduct();
        ProcessingPlanDocumentEntity.PlanItem materialItem = new ProcessingPlanDocumentEntity.PlanItem();
        materialItem.setProduct(material);
        DecimalFormat df = new DecimalFormat("#.####");
        materialItem.setQuantity(Double.valueOf(df.format(randomDouble(1, 5, 4))));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProductEntity product = simpleEntityFactory.createSimpleProduct();
        ProcessingPlanDocumentEntity.PlanItem productItem = new ProcessingPlanDocumentEntity.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(Double.valueOf(df.format(randomDouble(1, 5, 4))));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(processingPlan);

        ProcessingOrderDocumentEntity processingOrder = api.entity().processingorder().newDocument("processingPlan", processingPlan);

        assertEquals("", processingOrder.getName());
        assertEquals((Double) 1.0, processingOrder.getQuantity());
        assertEquals(processingPlan.getMeta().getHref(), processingOrder.getProcessingPlan().getMeta().getHref());
        assertEquals(processingPlan.getMaterials().getMeta().getSize(), (Integer) processingOrder.getPositions().getRows().size());
        assertEquals(material.getMeta().getHref(), ((ProductEntity) processingOrder.getPositions().getRows().get(0).getAssortment()).getMeta().getHref());
        assertEquals(materialItem.getQuantity(), processingOrder.getPositions().getRows().get(0).getQuantity());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        Optional<OrganizationEntity> orgOptional = orgList.getRows().stream().
                min(Comparator.comparing(OrganizationEntity::getCreated));

        OrganizationEntity org = null;
        if (orgOptional.isPresent()) {
            org = orgOptional.get();
        } else {
            // Должно быть первое созданное юрлицо
            fail();
        }

        assertEquals(processingOrder.getOrganization().getMeta().getHref(), org.getMeta().getHref());

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        assertEquals(processingOrder.getStore().getMeta().getHref(), store.getRows().get(0).getMeta().getHref());
    }

    private void getAsserts(ProcessingOrderDocumentEntity processingOrder, ProcessingOrderDocumentEntity retrievedEntity) {
        assertEquals(processingOrder.getName(), retrievedEntity.getName());
        assertEquals(processingOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(processingOrder.getProcessingPlan().getMeta().getHref(), retrievedEntity.getProcessingPlan().getMeta().getHref());
        assertEquals(processingOrder.getPositions().getMeta().getSize(), retrievedEntity.getPositions().getMeta().getSize());
    }

    private void putAsserts(ProcessingOrderDocumentEntity processingOrder, ProcessingOrderDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity retrievedUpdatedEntity = api.entity().processingorder().get(processingOrder.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getProcessingPlan().getMeta().getHref(), retrievedUpdatedEntity.getProcessingPlan().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getPositions().getMeta().getSize(), retrievedUpdatedEntity.getPositions().getMeta().getSize());
    }
}
