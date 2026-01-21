package com.example.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PokemonDatabase_Impl extends PokemonDatabase {
  private volatile PokemonDao _pokemonDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `pokemon` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `imageUrl` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `pokemon_detail` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `imageUrl` TEXT NOT NULL, `height` INTEGER NOT NULL, `weight` INTEGER NOT NULL, `types` TEXT NOT NULL, `stats` TEXT NOT NULL, `abilities` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a8221f8f2091d9439b795238ac0d8086')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `pokemon`");
        db.execSQL("DROP TABLE IF EXISTS `pokemon_detail`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPokemon = new HashMap<String, TableInfo.Column>(3);
        _columnsPokemon.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPokemon.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPokemon.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPokemon = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPokemon = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPokemon = new TableInfo("pokemon", _columnsPokemon, _foreignKeysPokemon, _indicesPokemon);
        final TableInfo _existingPokemon = TableInfo.read(db, "pokemon");
        if (!_infoPokemon.equals(_existingPokemon)) {
          return new RoomOpenHelper.ValidationResult(false, "pokemon(com.example.data.local.entity.PokemonEntity).\n"
                  + " Expected:\n" + _infoPokemon + "\n"
                  + " Found:\n" + _existingPokemon);
        }
        final HashMap<String, TableInfo.Column> _columnsPokemonDetail = new HashMap<String, TableInfo.Column>(8);
        _columnsPokemonDetail.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPokemonDetail.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPokemonDetail.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPokemonDetail.put("height", new TableInfo.Column("height", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPokemonDetail.put("weight", new TableInfo.Column("weight", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPokemonDetail.put("types", new TableInfo.Column("types", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPokemonDetail.put("stats", new TableInfo.Column("stats", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPokemonDetail.put("abilities", new TableInfo.Column("abilities", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPokemonDetail = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPokemonDetail = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPokemonDetail = new TableInfo("pokemon_detail", _columnsPokemonDetail, _foreignKeysPokemonDetail, _indicesPokemonDetail);
        final TableInfo _existingPokemonDetail = TableInfo.read(db, "pokemon_detail");
        if (!_infoPokemonDetail.equals(_existingPokemonDetail)) {
          return new RoomOpenHelper.ValidationResult(false, "pokemon_detail(com.example.data.local.entity.PokemonDetailEntity).\n"
                  + " Expected:\n" + _infoPokemonDetail + "\n"
                  + " Found:\n" + _existingPokemonDetail);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a8221f8f2091d9439b795238ac0d8086", "15efb661a6b0eed8500f5bf84a6b3b2c");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "pokemon","pokemon_detail");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `pokemon`");
      _db.execSQL("DELETE FROM `pokemon_detail`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PokemonDao.class, PokemonDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PokemonDao pokemonDao() {
    if (_pokemonDao != null) {
      return _pokemonDao;
    } else {
      synchronized(this) {
        if(_pokemonDao == null) {
          _pokemonDao = new PokemonDao_Impl(this);
        }
        return _pokemonDao;
      }
    }
  }
}
