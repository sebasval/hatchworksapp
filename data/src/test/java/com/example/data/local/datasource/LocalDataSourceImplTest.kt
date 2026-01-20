package com.example.data.local.datasource

import app.cash.turbine.test
import com.example.data.local.database.PokemonDao
import com.example.data.local.entity.PokemonDetailEntity
import com.example.data.local.entity.PokemonEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalDataSourceImplTest {

    private fun providesDao(): PokemonDao = mockk(relaxed = true)

    private fun providesDataSource(
        dao: PokemonDao = providesDao()
    ): LocalDataSourceImpl = LocalDataSourceImpl(dao)

    private fun providesPokemonEntity(
        id: Int = 1,
        name: String = "bulbasaur"
    ): PokemonEntity = PokemonEntity(
        id = id,
        name = name,
        imageUrl = "https://example.com/$id.png"
    )

    private fun providesPokemonEntityList(count: Int = 3): List<PokemonEntity> =
        (1..count).map { providesPokemonEntity(id = it, name = "pokemon$it") }

    private fun providesPokemonDetailEntity(
        id: Int = 1,
        name: String = "bulbasaur"
    ): PokemonDetailEntity = PokemonDetailEntity(
        id = id,
        name = name,
        imageUrl = "https://example.com/$id.png",
        height = 7,
        weight = 69,
        types = "grass,poison",
        stats = "hp:45",
        abilities = "overgrow"
    )

    @Test
    fun `getPokemonList returns flow from dao`() = runTest {
        val dao = providesDao()
        val dataSource = providesDataSource(dao)
        val entities = providesPokemonEntityList(2)

        coEvery { dao.getPokemonList() } returns flowOf(entities)

        dataSource.getPokemonList().test {
            assertEquals(entities, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `savePokemonList calls dao insert`() = runTest {
        val dao = providesDao()
        val dataSource = providesDataSource(dao)
        val entities = providesPokemonEntityList(2)

        dataSource.savePokemonList(entities)

        coVerify { dao.insertPokemonList(entities) }
    }

    @Test
    fun `getPokemonDetail returns flow from dao`() = runTest {
        val dao = providesDao()
        val dataSource = providesDataSource(dao)
        val entity = providesPokemonDetailEntity()

        coEvery { dao.getPokemonDetail(1) } returns flowOf(entity)

        dataSource.getPokemonDetail(1).test {
            assertEquals(entity, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `savePokemonDetail calls dao insert`() = runTest {
        val dao = providesDao()
        val dataSource = providesDataSource(dao)
        val entity = providesPokemonDetailEntity()

        dataSource.savePokemonDetail(entity)

        coVerify { dao.insertPokemonDetail(entity) }
    }
}
