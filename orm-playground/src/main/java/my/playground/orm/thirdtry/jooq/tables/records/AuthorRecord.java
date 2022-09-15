/*
 * This file is generated by jOOQ.
 */
package my.playground.orm.thirdtry.jooq.tables.records;


import my.playground.orm.thirdtry.jooq.tables.Author;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AuthorRecord extends UpdatableRecordImpl<AuthorRecord> implements Record3<Integer, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LIBRARY.AUTHOR.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>LIBRARY.AUTHOR.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>LIBRARY.AUTHOR.FIRST_NAME</code>.
     */
    public void setFirstName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>LIBRARY.AUTHOR.FIRST_NAME</code>.
     */
    public String getFirstName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LIBRARY.AUTHOR.LAST_NAME</code>.
     */
    public void setLastName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>LIBRARY.AUTHOR.LAST_NAME</code>.
     */
    public String getLastName() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Author.AUTHOR.ID;
    }

    @Override
    public Field<String> field2() {
        return Author.AUTHOR.FIRST_NAME;
    }

    @Override
    public Field<String> field3() {
        return Author.AUTHOR.LAST_NAME;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getFirstName();
    }

    @Override
    public String component3() {
        return getLastName();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getFirstName();
    }

    @Override
    public String value3() {
        return getLastName();
    }

    @Override
    public AuthorRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public AuthorRecord value2(String value) {
        setFirstName(value);
        return this;
    }

    @Override
    public AuthorRecord value3(String value) {
        setLastName(value);
        return this;
    }

    @Override
    public AuthorRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuthorRecord
     */
    public AuthorRecord() {
        super(Author.AUTHOR);
    }

    /**
     * Create a detached, initialised AuthorRecord
     */
    public AuthorRecord(Integer id, String firstName, String lastName) {
        super(Author.AUTHOR);

        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
    }
}
