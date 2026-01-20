package com.example.data.local.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.data.local.entity.PokemonDetailEntity;
import com.example.data.local.entity.PokemonEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PokemonDao_Impl implements PokemonDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PokemonEntity> __insertionAdapterOfPokemonEntity;

  private final EntityInsertionAdapter<PokemonDetailEntity> __insertionAdapterOfPokemonDetailEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearPokemonList;

  private final SharedSQLiteStatement __preparedStmtOfClearPokemonDetails;

  public PokemonDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPokemonEntity = new EntityInsertionAdapter<PokemonEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pokemon` (`id`,`name`,`imageUrl`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PokemonEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getImageUrl());
      }
    };
    this.__insertionAdapterOfPokemonDetailEntity = new EntityInsertionAdapter<PokemonDetailEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pokemon_detail` (`id`,`name`,`imageUrl`,`height`,`weight`,`types`,`stats`,`abilities`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PokemonDetailEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getImageUrl());
        statement.bindLong(4, entity.getHeight());
        statement.bindLong(5, entity.getWeight());
        statement.bindString(6, entity.getTypes());
        statement.bindString(7, entity.getStats());
        statement.bindString(8, entity.getAbilities());
      }
    };
    this.__preparedStmtOfClearPokemonList = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pokemon";
        return _query;
      }
    };
    this.__preparedStmtOfClearPokemonDetails = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pokemon_detail";
        return _query;
      }
    };
  }

  @Override
  public Object insertPokemonList(final List<PokemonEntity> pokemonList,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPokemonEntity.insert(pokemonList);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPokemonDetail(final PokemonDetailEntity pokemonDetail,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPokemonDetailEntity.insert(pokemonDetail);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearPokemonList(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearPokemonList.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearPokemonList.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearPokemonDetails(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearPokemonDetails.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearPokemonDetails.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PokemonEntity>> getPokemonList() {
    final String _sql = "SELECT * FROM pokemon ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pokemon"}, new Callable<List<PokemonEntity>>() {
      @Override
      @NonNull
      public List<PokemonEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final List<PokemonEntity> _result = new ArrayList<PokemonEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PokemonEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            _item = new PokemonEntity(_tmpId,_tmpName,_tmpImageUrl);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<PokemonDetailEntity> getPokemonDetail(final int id) {
    final String _sql = "SELECT * FROM pokemon_detail WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pokemon_detail"}, new Callable<PokemonDetailEntity>() {
      @Override
      @Nullable
      public PokemonDetailEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "types");
          final int _cursorIndexOfStats = CursorUtil.getColumnIndexOrThrow(_cursor, "stats");
          final int _cursorIndexOfAbilities = CursorUtil.getColumnIndexOrThrow(_cursor, "abilities");
          final PokemonDetailEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            final int _tmpHeight;
            _tmpHeight = _cursor.getInt(_cursorIndexOfHeight);
            final int _tmpWeight;
            _tmpWeight = _cursor.getInt(_cursorIndexOfWeight);
            final String _tmpTypes;
            _tmpTypes = _cursor.getString(_cursorIndexOfTypes);
            final String _tmpStats;
            _tmpStats = _cursor.getString(_cursorIndexOfStats);
            final String _tmpAbilities;
            _tmpAbilities = _cursor.getString(_cursorIndexOfAbilities);
            _result = new PokemonDetailEntity(_tmpId,_tmpName,_tmpImageUrl,_tmpHeight,_tmpWeight,_tmpTypes,_tmpStats,_tmpAbilities);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
