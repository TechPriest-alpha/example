/*
 * This file is generated by jOOQ.
 */
package my.playground.orm.thirdtry.tables;


import my.playground.orm.thirdtry.DefaultSchema;
import my.playground.orm.thirdtry.Keys;
import my.playground.orm.thirdtry.tables.records.JointableparentRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Jointableparent extends TableImpl<JointableparentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>JoinTableParent</code>
     */
    public static final Jointableparent JOINTABLEPARENT = new Jointableparent();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JointableparentRecord> getRecordType() {
        return JointableparentRecord.class;
    }

    /**
     * The column <code>JoinTableParent.id</code>.
     */
    public final TableField<JointableparentRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>JoinTableParent.parentData</code>.
     */
    public final TableField<JointableparentRecord, String> PARENTDATA = createField(DSL.name("parentData"), SQLDataType.VARCHAR(255), this, "");

    private Jointableparent(Name alias, Table<JointableparentRecord> aliased) {
        this(alias, aliased, null);
    }

    private Jointableparent(Name alias, Table<JointableparentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>JoinTableParent</code> table reference
     */
    public Jointableparent(String alias) {
        this(DSL.name(alias), JOINTABLEPARENT);
    }

    /**
     * Create an aliased <code>JoinTableParent</code> table reference
     */
    public Jointableparent(Name alias) {
        this(alias, JOINTABLEPARENT);
    }

    /**
     * Create a <code>JoinTableParent</code> table reference
     */
    public Jointableparent() {
        this(DSL.name("JoinTableParent"), null);
    }

    public <O extends Record> Jointableparent(Table<O> child, ForeignKey<O, JointableparentRecord> key) {
        super(child, key, JOINTABLEPARENT);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<JointableparentRecord> getPrimaryKey() {
        return Keys.JOINTABLEPARENT__;
    }

    @Override
    public Jointableparent as(String alias) {
        return new Jointableparent(DSL.name(alias), this);
    }

    @Override
    public Jointableparent as(Name alias) {
        return new Jointableparent(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Jointableparent rename(String name) {
        return new Jointableparent(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Jointableparent rename(Name name) {
        return new Jointableparent(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
