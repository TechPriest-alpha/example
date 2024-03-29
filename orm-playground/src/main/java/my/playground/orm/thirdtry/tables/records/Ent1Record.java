/*
 * This file is generated by jOOQ.
 */
package my.playground.orm.thirdtry.tables.records;


import my.playground.orm.thirdtry.tables.Ent1;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Ent1Record extends UpdatableRecordImpl<Ent1Record> implements Record2<Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>Ent1.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>Ent1.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>Ent1.ent3_id</code>.
     */
    public void setEnt3Id(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>Ent1.ent3_id</code>.
     */
    public Integer getEnt3Id() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Ent1.ENT1.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Ent1.ENT1.ENT3_ID;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getEnt3Id();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getEnt3Id();
    }

    @Override
    public Ent1Record value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public Ent1Record value2(Integer value) {
        setEnt3Id(value);
        return this;
    }

    @Override
    public Ent1Record values(Integer value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached Ent1Record
     */
    public Ent1Record() {
        super(Ent1.ENT1);
    }

    /**
     * Create a detached, initialised Ent1Record
     */
    public Ent1Record(Integer id, Integer ent3Id) {
        super(Ent1.ENT1);

        setId(id);
        setEnt3Id(ent3Id);
    }
}
