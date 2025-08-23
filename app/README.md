# News App (MVVM, Jetpack Compose, Hilt, Room, Paging 3, Retrofit)

Приложение для отображения новостной ленты с возможностью поиска и сохранения статей.  
Архитектура построена на **MVVM** с разделением на слои **UI – Domain – Data**.

---

## Архитектура и структура пакетов

```
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
```

---

## Data Flow

### 1. **UI Layer (Compose)**
- Построен на **Jetpack Compose**  
- Работает с **ViewModel**, который отдает состояние через `StateFlow`

### 2. **Domain Layer**
- Содержит **use cases** в `NewsUsecases`
- Основные операции:
  - `getNews`
  - `searchNews`
  - `upsertArticle`
  - `getSavedArticles`

### 3. **Data Layer**
- `NewsRepositoryImplementation` соединяет **Remote API** и **Room**
- Использует **PagingSource** для постраничной загрузки

### 4. **Remote Layer**
- Источник данных: **NewsAPI.org**
- **Retrofit + Paging 3**

### 5. **Local Layer**
- **Room**, таблица `Article`
- Операции:
  - `@Insert(onConflict = REPLACE)`
  - `@Delete`
  - `@Query`
- Сохранение статей при нажатии на **Bookmark** в `DetailsScreen`

---

## Поиск

- Работает через `everything` эндпоинт NewsAPI  
- Параметры:
  - `q` (ключевое слово)
  - `sources`
  - `page`
  - `apiKey`
- Поиск идет по: `title`, `description`, `content`

---

## Dependency Injection (Hilt)

В `AppModule.kt` настраивается DI:  
- `UserPreferences`  
- `Room`  
- `Retrofit`  
- `NewsRepository`  
- `NewsUsecases`  

Все `ViewModel` получают зависимости через `@HiltViewModel`.

---

## Навигация

- Реализована через `NavHost` и `NavController`  
- Для передачи данных между экранами используется `savedStateHandle`  
- В `DetailsScreen` принимается `article` через `savedStateHandle["article"]`
