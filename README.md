

------------ UI -----------
1. Home Screen

## Shows SearchBar + Products banner, sale banners, categories

 Interaction with SearchBar :-
    # when(user clicks on searchbar) {
        if(tap once) --> shows previous search results
        if(start writing) --> after 5 ms, make the search
    }
    # when click on the search icon after typing the query --> the bottom product banner will disappear and shows 
        list of products

## Shows SearchBar + list of products

------------------------------

app
├── data
│   ├── local
│   │   ├── dao
│   │   ├── entities
│   │   └── LocalProductDataSource.kt
│   │
│   ├── remote
│   │   ├── api
│   │   ├── dtos
│   │   └── RemoteProductDataSource.kt
│   │
│   ├── repository
│   │   └── ProductRepositoryImpl.kt
│   │
│   └── mapper
│       └── ProductMapper.kt
│
├── domain
│   ├── model
│   │   └── Product.kt
│   │
│   ├── repository
│   │   └── ProductRepository.kt
│   │
│   └── usecase
│       ├── SearchProductsUseCase.kt
│       ├── GetSaleProductsUseCase.kt
│       └── GetAllProductsUseCase.kt
│
├── di
│   └── AppModule.kt (Hilt bindings)
│
├── presentation
│   └── productlist
│       ├── ProductListViewModel.kt
│       └── ProductListScreen.kt