

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



Implement a Debounce Search using Kotlin Coroutines

I wants to create a ecommerce app
Features:

1st screen : Search Page
Search page have a searchbar on the top and list of products at the bottom
Initially:
the products are empty so show no products



Search-bar features:
Search bar where user can search for a product by typing it.
shows max 7 suggestions when user starts typing
can remember last 5 searches(called recent searches) will be shown in 2 scenarios:
when user just tap on searchbar and not started typing
when the query characters matches with the recent searches
when the user start typing api call will be made to fetch the term related to that query
but call will be made after few milli sec using (API call throttling)
Only 7 suggestions will be shown in the search bar not more than that including max 3 recent searches from db and rest api suggestions


based on category: like milk is in food category, tv in electronic category
Save search history(last 5 searches), suggest last 5 suggestions from the previous searches in case user doesn’t provide any input
but if queried for apple – so apple can be food or tv, so display them in 2 different stack list(categories)
Techstack:
compose
Hilt
Clean Architecture



📁 Project Structure & Packages

- Pure business logic, No Android deps here
1. domain/ 

model/

Product.kt

SearchSuggestion.kt

repository/

SearchRepository.kt

usecase/

SearchProductsUseCase.kt

GetCachedSuggestionsUseCase.kt

SaveSuggestionsUseCase.kt

2. data/ - Data sources: API, DB, cache
   remote/

SearchApiService.kt

RemoteDataSource.kt

local/

SearchSuggestionEntity.kt

SearchDao.kt

SearchDatabase.kt

LocalDataSource.kt

repository/

SearchRepositoryImpl.kt (implements domain SearchRepository)

3. presentation/ - Compose UI + ViewModels
   search/

SearchScreen.kt (UI)

SearchViewModel.kt

SearchUiState.kt

SearchEvent.kt (optional for MVI-style)

4. di/ - Dependency Injection with Hilt
   NetworkModule.kt

DatabaseModule.kt

RepositoryModule.kt

UseCaseModule.kt

5. navigation/
   ShopSphereNavGraph.kt (for NavHost)

Routes: "search" and eventually more like "productDetails/{id}"


