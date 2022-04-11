/*
 * This file is generated by jOOQ.
 */
package my.playground.orm.thirdtry.tables.records;


import my.playground.orm.thirdtry.tables.Jointableparent;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class JointableparentRecord extends UpdatableRecordImpl<JointableparentRecord> implements Record2<Long, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>JoinTableParent.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>JoinTableParent.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>JoinTableParent.parentData</code>.
     */
    public void setParentdata(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>JoinTableParent.parentData</code>.
     */
    public String getParentdata() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Jointableparent.JOINTABLEPARENT.ID;
    }

    @Override
    public Field<String> field2() {
        return Jointableparent.JOINTABLEPARENT.PARENTDATA;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getParentdata();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getParentdata();
    }

    @Override
    public JointableparentRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public JointableparentRecord value2(String value) {
        setParentdata(value);
        return this;
    }

    @Override
    public JointableparentRecord values(Long value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JointableparentRecord
     */
    public JointableparentRecord() {
        super(Jointableparent.JOINTABLEPARENT);
    }

    /**
     * Create a detached, initialised JointableparentRecord
     */
    public JointableparentRecord(Long id, String parentdata) {
        super(Jointableparent.JOINTABLEPARENT);

        setId(id);
        setParentdata(parentdata);
    }
}
