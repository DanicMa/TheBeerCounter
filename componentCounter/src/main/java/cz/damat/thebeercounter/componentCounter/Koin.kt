package cz.damat.thebeercounter.componentCounter

import cz.damat.thebeercounter.commonlib.room.AppDatabase
import cz.damat.thebeercounter.componentCounter.data.repositoryImpl.HistoryRepositoryImpl
import cz.damat.thebeercounter.componentCounter.data.repositoryImpl.ProductRepositoryImpl
import cz.damat.thebeercounter.componentCounter.domain.repository.HistoryRepository
import cz.damat.thebeercounter.componentCounter.domain.repository.ProductRepository
import org.koin.dsl.module


/**
 * Created by MD on 18.10.23.
 */
val componentCounterKoinModule = module {

    single { get<AppDatabase>().productDao() }
    single { get<AppDatabase>().historyItemDao() }

    single<ProductRepository> { ProductRepositoryImpl(get(), get(), get()) }
    single<HistoryRepository> { HistoryRepositoryImpl(get()) }
}