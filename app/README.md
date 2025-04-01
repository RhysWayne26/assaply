Проект построен на основе MVVM (Model-View-ViewModel) с использованием Jetpack Compose, Hilt, Room, Paging 3, и Retrofit. Приложение отображает новостную ленту с возможностью поиска и сохранения статей.

**Пакеты и слои**
├── api/              # Работа с удалённым API (NewsAPI)
├── room/             # База данных Room и DAO
├── repository/       # Репозиторий, соединяющий Room и API
├── domain/           # Сущности и use cases
├── presentation/     # UI, экраны, ViewModel'и
│   ├── screens/      # UI для каждого экрана (Home, Search, Details, Bookmark)
│   ├── viewmodels/   # ViewModel'и с бизнес-логикой
│   └── navigation/   # Навигация
├── util/             # Вспомогательные классы (Constants, Preferences)
└── di/               # DI-модуль (AppModule)


**Data Flow и слои**

1)UI Layer (Compose):
Использует ViewModel, получающий State через StateFlow.

2)Domain Layer:
Все бизнес-операции инкапсулированы в NewsUsecases, который содержит методы getNews, searchNews, upsertArticle, getSavedArticles и др.

3)Data Layer:
NewsRepositoryImplementation предоставляет данные из Remote API и Room, используя PagingSource.

4)Remote Layer:
Retrofit + Paging 3, источник данных: NewsAPI.org.

5)Local Layer:
Room, таблица Article, хранит избранные статьи 
Статьи сохраняются при нажатии на bookmark (в DetailsScreen).
Используются @Insert(onConflict = REPLACE), @Delete, @Query.


**Поиск**

Поиск работает через API everything NewsAPI
Параметры: q, sources, page, apiKey
Поиск идет title, description, content

**Dependency Injection (Hilt)**

DI настраивается в AppModule.kt:
Биндятся: UserPreferences, Room, Retrofit, NewsRepository, NewsUsecases
Все ViewModel получают зависимости через @HiltViewModel

**Навигация**

Используется NavHost, NavController
savedStateHandle передаёт Article между экранами
Экран DetailsScreen работает с savedStateHandle["article"]

