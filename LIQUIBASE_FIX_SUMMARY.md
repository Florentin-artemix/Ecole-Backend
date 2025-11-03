# Liquibase Migration Fix - Summary

## Problem
The application was failing to start with the error:
```
ERREUR: la relation « classe » existe déjà
```

This occurred because the `classe` table already existed in the PostgreSQL database, but Liquibase was trying to create it again. This happens when the database state is out of sync with the Liquibase changelog tracking.

## Solution
Added `preConditions` to all Liquibase changesets to make them idempotent. This means they will check if the database objects already exist before attempting to create/modify them.

### Files Modified

#### 1. `10-create-classe-table.xml`
- Added preCondition to skip table creation if `classe` table already exists

#### 2. `11-create-ecole-table.xml`
- Added preCondition to skip table creation if `ecole` table already exists

#### 3. `12-insert-default-data.xml`
- Added preConditions to skip inserting default classes and school data if records already exist

#### 4. `13-add-classe-id-to-cours.xml`
- Added preCondition to skip adding `classe_id` column if it already exists
- Added preCondition to skip creating foreign key if it already exists
- Added preCondition to skip creating index if it already exists

#### 5. `14-add-foreign-keys-to-eleve.xml`
- Added preCondition to skip adding `classe_id` column if it already exists
- Added preCondition to skip adding `ecole_id` column if it already exists
- Added preConditions to skip creating foreign keys if they already exist

#### 6. `15-cleanup-old-columns.xml`
- Added preConditions to skip dropping columns if they don't exist

## How PreConditions Work

The `preConditions` element with `onFail="MARK_RAN"` tells Liquibase:
1. Check if the condition is met before executing the changeset
2. If the condition fails (e.g., table already exists), mark the changeset as already executed
3. Skip the changeset execution to avoid errors

Example:
```xml
<changeSet id="010-create-classe-table" author="ecole">
    <preConditions onFail="MARK_RAN">
        <not>
            <tableExists tableName="classe"/>
        </not>
    </preConditions>
    <createTable tableName="classe">
        <!-- ... -->
    </createTable>
</changeSet>
```

## Benefits
- **Idempotent migrations**: Can be run multiple times safely
- **No manual intervention needed**: Liquibase will automatically skip already-applied changes
- **Flexible deployment**: Works with existing databases or fresh databases
- **Error prevention**: Prevents "already exists" errors

## Next Steps
Restart your Spring Boot application. Liquibase should now successfully process all changesets without errors.
