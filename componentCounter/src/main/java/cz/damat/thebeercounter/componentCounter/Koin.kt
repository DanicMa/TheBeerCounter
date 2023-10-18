package cz.damat.thebeercounter.componentCounter

import cz.damat.thebeercounter.commonlib.room.AppDatabase
import cz.damat.thebeercounter.componentCounter.data.repository_impl.HistoryRepositoryImpl
import cz.damat.thebeercounter.componentCounter.data.repository_impl.ProductRepositoryImpl
import cz.damat.thebeercounter.componentCounter.domain.repository.HistoryRepository
import cz.damat.thebeercounter.componentCounter.domain.repository.ProductRepository
import org.koin.dsl.module


/**
 * Created by MD on 18.10.23.
 */
val componentCounterKoinModule = module {

    // todo mod - to commonLib module or not?
    single { get<AppDatabase>().productDao() }
    single { get<AppDatabase>().historyItemDao() }

    single<ProductRepository> { ProductRepositoryImpl(get(), get(), get()) }
    single<HistoryRepository> { HistoryRepositoryImpl(get(), get()) }
}