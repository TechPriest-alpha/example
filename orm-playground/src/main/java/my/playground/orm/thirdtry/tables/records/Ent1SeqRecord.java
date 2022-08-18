/*
 * This file is generated by jOOQ.
 */
package my.playground.orm.thirdtry.tables.records;


import my.playground.orm.thirdtry.tables.Ent1Seq;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Ent1SeqRecord extends TableRecordImpl<Ent1SeqRecord> implements Record1<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>Ent1_SEQ.next_val</code>.
     */
    public void setNextVal(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>Ent1_SEQ.next_val</code>.
     */
    public Long getNextVal() {
        return (Long) get(0);
    }

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row1<Long> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    @Override
    public Row1<Long> valuesRow() {
        return (Row1) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Ent1Seq.ENT1_SEQ.NEXT_VAL;
    }

    @Override
    public Long component1() {
        return getNextVal();
    }

    @Override
    public Long value1() {
        return getNextVal();
    }

    @Override
    public Ent1SeqRecord value1(Long value) {
        setNextVal(value);
        return this;
    }

    @Override
    public Ent1SeqRecord values(Long value1) {
        value1(value1);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached Ent1SeqRecord
     */
    public Ent1SeqRecord() {
        super(Ent1Seq.ENT1_SEQ);
    }

    /**
     * Create a detached, initialised Ent1SeqRecord
     */
    public Ent1SeqRecord(Long nextVal) {
        super(Ent1Seq.ENT1_SEQ);

        setNextVal(nextVal);
    }
}