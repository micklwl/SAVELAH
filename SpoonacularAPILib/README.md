# Getting started

## How to Build

The generated code uses a few Gradle dependencies e.g., Jackson, Volley,
and Apache HttpClient. The reference to these dependencies is already
added in the build.gradle file will be installed automatically. Therefore,
you will need internet access for a successful build.

* In order to open the client library in Android Studio click on ``` Open an Existing Android Project ```.

![Importing SDK into Android Studio - Step 1](https://apidocs.io/illustration/android?step=import1&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

* Browse to locate the folder containing the source code. Select the location of the SpoonacularAPI gradle project and click ``` Ok ```.

![Importing SDK into Android Studio - Step 2](https://apidocs.io/illustration/android?step=import2&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

* Upon successful import, the project can be built by clicking on ``` Build > Make Project ``` or  pressing ``` Ctrl + F9 ```.

![Importing SDK into Android Studio - Step 3](https://apidocs.io/illustration/android?step=import3&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

## How to Use

The following section explains how to use the SpoonacularAPI library in a new project.

### 1. Starting a new project 

For starting a new project, click on ``` Create New Android Studio Project ```.

![Add a new project in Android Studio - Step 1](https://apidocs.io/illustration/android?step=createNewProject0&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

Here, configure the new project by adding the name, domain and location of the sample application followed by clicking ``` Next ```.

![Create a new Android Studio Project - Step 2](https://apidocs.io/illustration/android?step=createNewProject1&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

Following this, select the ``` Phone and Tablet ```` option as shown in the illustration below and click ``` Next ```. 

![Create a new Android Studio Project - Step 3](https://apidocs.io/illustration/android?step=createNewProject2&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

In the following step, choose ``` Empty Activity ``` as the activity type and click ``` Next ```.

![Create a new Android Studio Project - Step 4](https://apidocs.io/illustration/android?step=createNewProject3&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

In this step, provide an ``` Activity Name ``` and ``` Layout Name ``` and click ``` Finish ```.  This would take you to the newly created project.

![Create a new Android Studio Project - Step 4](https://apidocs.io/illustration/android?step=createNewProject4&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

### 2. Add reference of the library project

In order to add a dependency to this sample application, click on the android button shown in the project explorer on the left side as shown in the picture. Click on ``` Project ``` in the drop down that emerges.  

![Adding dependency to the client library - Step 1](https://apidocs.io/illustration/android?step=testProject0&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

Right click the sample application in the project explorer and click on ``` New > Module ```  as shown in the picture.

![Adding dependency to the client library - Step 2](https://apidocs.io/illustration/android?step=testProject1&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

Choose ``` Import Gradle Project ``` and click ``` Next ```.

![Adding dependency to the client library - Step 3](https://apidocs.io/illustration/android?step=testProject2&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

Click on ``` Finish ``` which would take you back to the sample application with the refernced SDK. 

![Adding dependency to the client library - Step 4](https://apidocs.io/illustration/android?step=testProject3&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

In the following step naigate to the ``` SampleApplication >  app > build.gradle ``` file and add the following line ```compile project(path: ':SpoonacularAPI')``` to the dependencies section as shown in the illustration below.

![Adding dependency to the client library - Step 5](https://apidocs.io/illustration/android?step=testProject4&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

Finally, press ``` Sync Now ``` in the warning visible as shown in the picture below.

![Adding dependency to the client library - Step 6](https://apidocs.io/illustration/android?step=testProject5&workspaceFolder=spoonacular%20API&workspaceName=SpoonacularAPI&projectName=SpoonacularAPILib&rootNamespace=com.mashape.p.spoonacularrecipefoodnutritionv1)

### 3. Write sample code

Once the ``` SampleApplication ``` is created, a file named ``` SampleApplication > app > src > main > java > MainActivity ``` will be visible in the *Project Explorer* with an ``` onCreate ``` method. This is the entry point for the execution of the created project.
Here, you can add code to initialize the client library and instantiate a *Controller* class. Sample code to initialize the client library and using controller methods is given in the subsequent sections.

## How to Test

The generated code and the server can be tested using automatically generated test cases. 
JUnit is used as the testing framework and test runner.

In Android Studio, for running the tests do the following:

1. Right click on *SampleApplication > SpoonacularAPILib > androidTest > java)* from the project explorer.
2. Select "Run All Tests" or use "Ctrl + Shift + F10" to run the Tests.

## Initialization

### 
You need the following information for initializing the API client.

| Parameter | Description |
|-----------|-------------|
| xMashapeKey | The Mashape application you want to use for this session. |



API client can be initialized as following. The `appContext` being passed is the Android application [`Context`](https://developer.android.com/reference/android/content/Context.html).

```java
// Configuration parameters
String xMashapeKey = "xMashapeKey"; // The Mashape application you want to use for this session.

com.mashape.p.spoonacularrecipefoodnutritionv1.Configuration.initialize(appContext);
SpoonacularAPIClient client = new SpoonacularAPIClient(xMashapeKey);
```

## Class Reference

### <a name="list_of_controllers"></a>List of Controllers

* [APIController](#api_controller)

### <a name="api_controller"></a>![Class: ](https://apidocs.io/img/class.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController") APIController

#### Get singleton instance

The singleton instance of the ``` APIController ``` class can be accessed from the API Client.

```java
APIController client = client.getClient();
```

#### <a name="get_product_information_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.getProductInformationAsync") getProductInformationAsync

> *Tags:*  ``` Skips Authentication ``` 

> Get information about a packaged food product.


```java
void getProductInformationAsync(
        final int id,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| id |  ``` Required ```  ``` DefaultValue ```  | The id of the packaged food product. |


#### Example Usage

```java
int id = 22347;
// Invoking the API call with sample inputs
client.getProductInformationAsync(id, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="find_similar_recipes_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.findSimilarRecipesAsync") findSimilarRecipesAsync

> *Tags:*  ``` Skips Authentication ``` 

> Find recipes which are similar to the given one.


```java
void findSimilarRecipesAsync(
        final int id,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| id |  ``` Required ```  ``` DefaultValue ```  | The id of the source recipe to which similar recipes should be found. |


#### Example Usage

```java
int id = 156992;
// Invoking the API call with sample inputs
client.findSimilarRecipesAsync(id, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="get_autocomplete_ingredient_search_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.getAutocompleteIngredientSearchAsync") getAutocompleteIngredientSearchAsync

> *Tags:*  ``` Skips Authentication ``` 

> Autocomplete a search for an ingredient.


```java
void getAutocompleteIngredientSearchAsync(
        final String query,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| query |  ``` Required ```  ``` DefaultValue ```  | The query - a partial or full ingredient name. |


#### Example Usage

```java
String query = "appl";
// Invoking the API call with sample inputs
client.getAutocompleteIngredientSearchAsync(query, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="create_visualize_price_breakdown_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.createVisualizePriceBreakdownAsync") createVisualizePriceBreakdownAsync

> *Tags:*  ``` Skips Authentication ``` 

> Visualize the price breakdown of a recipe.


```java
void createVisualizePriceBreakdownAsync(
        final String ingredientList,
        final int servings,
        final String defaultCss,
        final Integer mode,
        Map<String, Object> fieldParameters,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| ingredientList |  ``` Required ```  ``` DefaultValue ```  | The ingredient list of the recipe, one ingredient per line. |
| servings |  ``` Required ```  ``` DefaultValue ```  | The number of servings. |
| defaultCss |  ``` Optional ```  ``` DefaultValue ```  | Whether the widget should be styled with the default css. |
| mode |  ``` Optional ```  ``` DefaultValue ```  | The mode in which the widget should be delivered. 1 = separate views (compact), 2 = all in one view (full). |
| fieldParameters | ``` Optional ``` | Additional optional form parameters are supported by this method |


#### Example Usage

```java
String ingredientList = "3 oz flour";
int servings = 2;
String defaultCss = "checked";
Integer mode = 1;
// key-value map for optional form parameters
Map<String, Object> formParams = new LinkedHashMap<String, Object>();
// Invoking the API call with sample inputs
client.createVisualizePriceBreakdownAsync(ingredientList, servings, defaultCss, mode, formParams, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="create_visualize_nutrition_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.createVisualizeNutritionAsync") createVisualizeNutritionAsync

> *Tags:*  ``` Skips Authentication ``` 

> Visualize a recipe's nutritional information.


```java
void createVisualizeNutritionAsync(
        final String ingredientList,
        final int servings,
        final String defaultCss,
        Map<String, Object> fieldParameters,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| ingredientList |  ``` Required ```  ``` DefaultValue ```  | The ingredient list of the recipe, one ingredient per line. |
| servings |  ``` Required ```  ``` DefaultValue ```  | The number of servings. |
| defaultCss |  ``` Optional ```  ``` DefaultValue ```  | Whether the ingredient list should be styled with the default css. |
| fieldParameters | ``` Optional ``` | Additional optional form parameters are supported by this method |


#### Example Usage

```java
String ingredientList = "3 oz flour";
int servings = 2;
String defaultCss = "checked";
// key-value map for optional form parameters
Map<String, Object> formParams = new LinkedHashMap<String, Object>();
// Invoking the API call with sample inputs
client.createVisualizeNutritionAsync(ingredientList, servings, defaultCss, formParams, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="create_visualize_ingredients_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.createVisualizeIngredientsAsync") createVisualizeIngredientsAsync

> *Tags:*  ``` Skips Authentication ``` 

> Visualize ingredients of a recipe.


```java
void createVisualizeIngredientsAsync(
        final String ingredientList,
        final int servings,
        final String defaultCss,
        final String measure,
        final String view,
        Map<String, Object> fieldParameters,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| ingredientList |  ``` Required ```  ``` DefaultValue ```  | The ingredient list of the recipe, one ingredient per line. |
| servings |  ``` Required ```  ``` DefaultValue ```  | The initial number of servings. |
| defaultCss |  ``` Optional ```  ``` DefaultValue ```  | Whether the ingredient list should be styled with the default css. |
| measure |  ``` Optional ```  ``` DefaultValue ```  | The initial measure, either "metric" or "us". |
| view |  ``` Optional ```  ``` DefaultValue ```  | The initial view, either "grid" or "list". |
| fieldParameters | ``` Optional ``` | Additional optional form parameters are supported by this method |


#### Example Usage

```java
String ingredientList = "3 oz flour";
int servings = 2;
String defaultCss = "checked";
String measure = "metric";
String view = "grid";
// key-value map for optional form parameters
Map<String, Object> formParams = new LinkedHashMap<String, Object>();
// Invoking the API call with sample inputs
client.createVisualizeIngredientsAsync(ingredientList, servings, defaultCss, measure, view, formParams, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="get_summarize_recipe_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.getSummarizeRecipeAsync") getSummarizeRecipeAsync

> *Tags:*  ``` Skips Authentication ``` 

> Summarize the recipe in a short text.


```java
void getSummarizeRecipeAsync(
        final int id,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| id |  ``` Required ```  ``` DefaultValue ```  | The id of the recipe that should be summarized. |


#### Example Usage

```java
int id = 4632;
// Invoking the API call with sample inputs
client.getSummarizeRecipeAsync(id, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="search_grocery_products_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.searchGroceryProductsAsync") searchGroceryProductsAsync

> *Tags:*  ``` Skips Authentication ``` 

> Search packaged food products like frozen pizza and snickers bars.


```java
void searchGroceryProductsAsync(
        final String query,
        final Integer number,
        final Integer offset,
        Map<String, Object> queryParameters,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| query |  ``` Required ```  ``` DefaultValue ```  | The search query. |
| number |  ``` Optional ```  ``` DefaultValue ```  | The number of results to retrieve, defaults to 10. |
| offset |  ``` Optional ```  ``` DefaultValue ```  | The number of results to skip, defaults to 0. |
| queryParameters | ``` Optional ``` | Additional optional query parameters are supported by this method |


#### Example Usage

```java
String query = "snickers";
Integer number = 10;
Integer offset = 0;
// key-value map for optional query parameters
Map<String, Object> queryParams = new LinkedHashMap<String, Object>();
// Invoking the API call with sample inputs
client.searchGroceryProductsAsync(query, number, offset, queryParams, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="get_quick_answer_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.getQuickAnswerAsync") getQuickAnswerAsync

> *Tags:*  ``` Skips Authentication ``` 

> Answer a nutrition related natural language question.


```java
void getQuickAnswerAsync(
        final String q,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| q |  ``` Required ```  ``` DefaultValue ```  | The nutrition-related question. |


#### Example Usage

```java
String q = "How much vitamin c is in 2 apples?";
// Invoking the API call with sample inputs
client.getQuickAnswerAsync(q, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="get_recipe_information_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.getRecipeInformationAsync") getRecipeInformationAsync

> *Tags:*  ``` Skips Authentication ``` 

> Get information about a recipe.


```java
void getRecipeInformationAsync(
        final int id,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| id |  ``` Required ```  ``` DefaultValue ```  | The id of the recipe. |


#### Example Usage

```java
int id = 156992;
// Invoking the API call with sample inputs
client.getRecipeInformationAsync(id, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="create_parse_ingredients_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.createParseIngredientsAsync") createParseIngredientsAsync

> *Tags:*  ``` Skips Authentication ``` 

> Extract an ingredient from plain text.


```java
void createParseIngredientsAsync(
        final String ingredientList,
        final int servings,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| ingredientList |  ``` Required ```  ``` DefaultValue ```  | The ingredient list of the recipe, one ingredient per line. |
| servings |  ``` Required ```  ``` DefaultValue ```  | The number of servings that you can make from the ingredients. |


#### Example Usage

```java
String ingredientList = "3 oz pork shoulder";
int servings = 2;
// Invoking the API call with sample inputs
client.createParseIngredientsAsync(ingredientList, servings, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="create_map_ingredients_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.createMapIngredientsAsync") createMapIngredientsAsync

> *Tags:*  ``` Skips Authentication ``` 

> Map ingredients to food products.


```java
void createMapIngredientsAsync(
        final String ingredientList,
        final int servings,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| ingredientList |  ``` Required ```  ``` DefaultValue ```  | A new line-separated list of ingredients. |
| servings |  ``` Required ```  ``` DefaultValue ```  | The number of servings this recipe makes. |


#### Example Usage

```java
String ingredientList = "200g flour\\n3 eggs";
int servings = 1;
// Invoking the API call with sample inputs
client.createMapIngredientsAsync(ingredientList, servings, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="get_extract_recipe_from_website_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.getExtractRecipeFromWebsiteAsync") getExtractRecipeFromWebsiteAsync

> *Tags:*  ``` Skips Authentication ``` 

> Extract recipe data from a recipe blog or Web page.


```java
void getExtractRecipeFromWebsiteAsync(
        final String url,
        final Boolean forceExtraction,
        Map<String, Object> queryParameters,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| url |  ``` Required ```  ``` DefaultValue ```  | The URL of the recipe page. |
| forceExtraction |  ``` Optional ```  ``` DefaultValue ```  | If true, the extraction will be triggered no matter whether we know the recipe already. Use that only if information is missing as this operation is slower. |
| queryParameters | ``` Optional ``` | Additional optional query parameters are supported by this method |


#### Example Usage

```java
String url = "http://www.melskitchencafe.com/the-best-fudgy-brownies/";
Boolean forceExtraction = false;
// key-value map for optional query parameters
Map<String, Object> queryParams = new LinkedHashMap<String, Object>();
// Invoking the API call with sample inputs
client.getExtractRecipeFromWebsiteAsync(url, forceExtraction, queryParams, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="get_compute_daily_meal_plan_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.getComputeDailyMealPlanAsync") getComputeDailyMealPlanAsync

> *Tags:*  ``` Skips Authentication ``` 

> Compute a meal plan for a day.


```java
void getComputeDailyMealPlanAsync(
        final int targetCalories,
        final String timeFrame,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| targetCalories |  ``` Required ```  ``` DefaultValue ```  | The target number of calories per day. |
| timeFrame |  ``` Required ```  ``` DefaultValue ```  | For one day or a complete week, allowed values are "day" and "week". |


#### Example Usage

```java
int targetCalories = 2000;
String timeFrame = "day";
// Invoking the API call with sample inputs
client.getComputeDailyMealPlanAsync(targetCalories, timeFrame, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="create_classify_grocery_products_batch_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.createClassifyGroceryProductsBatchAsync") createClassifyGroceryProductsBatchAsync

> *Tags:*  ``` Skips Authentication ``` 

> Given a set of product jsons, get back classified products.


```java
void createClassifyGroceryProductsBatchAsync(
        final List<Productjsonarray> productJsonArray,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| productJsonArray |  ``` Required ```  ``` Collection ```  | A JSON Array of products. |


#### Example Usage

```java
try {
    List<Productjsonarray> productJsonArray = new ArrayList<Productjsonarray>();
    // Invoking the API call with sample inputs
    client.createClassifyGroceryProductsBatchAsync(productJsonArray, new APICallBack<DynamicResponse>() {
        public void onSuccess(HttpContext context, DynamicResponse response) {
            // TODO success callback handler
        }
        public void onFailure(HttpContext context, Throwable error) {
            // TODO failure callback handler
        }
    });
} catch(JsonProcessingException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
```


#### <a name="create_classify_cuisine_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.createClassifyCuisineAsync") createClassifyCuisineAsync

> *Tags:*  ``` Skips Authentication ``` 

> Classify the recipe's cuisine.


```java
void createClassifyCuisineAsync(
        final String ingredientList,
        final String title,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| ingredientList |  ``` Required ```  ``` DefaultValue ```  | The ingredient list of the recipe, one ingredient per line. |
| title |  ``` Required ```  ``` DefaultValue ```  | The title of the recipe. |


#### Example Usage

```java
String ingredientList = "3 oz pork shoulder";
String title = "Pork roast with green beans";
// Invoking the API call with sample inputs
client.createClassifyCuisineAsync(ingredientList, title, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="create_classify_a_grocery_product_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.createClassifyAGroceryProductAsync") createClassifyAGroceryProductAsync

> *Tags:*  ``` Skips Authentication ``` 

> Given a grocery product title, this endpoint allows you to detect what basic ingredient it is.


```java
void createClassifyAGroceryProductAsync(
        final Productjson productJson,
        final APICallBack<Classifiedproduct> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| productJson |  ``` Required ```  | The json representation of a product. |


#### Example Usage

```java
try {
    Productjson productJson = new Productjson();
    // Invoking the API call with sample inputs
    client.createClassifyAGroceryProductAsync(productJson, new APICallBack<Classifiedproduct>() {
        public void onSuccess(HttpContext context, Classifiedproduct response) {
            // TODO success callback handler
        }
        public void onFailure(HttpContext context, Throwable error) {
            // TODO failure callback handler
        }
    });
} catch(JsonProcessingException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
```


#### <a name="search_recipes_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.searchRecipesAsync") searchRecipesAsync

> *Tags:*  ``` Skips Authentication ``` 

> Search recipes in natural language.


```java
void searchRecipesAsync(
        final String query,
        final String cuisine,
        final String diet,
        final String excludeIngredients,
        final String intolerances,
        final Boolean limitLicense,
        final Integer number,
        final Integer offset,
        final String type,
        Map<String, Object> queryParameters,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| query |  ``` Required ```  ``` DefaultValue ```  | The (natural language) recipe search query. |
| cuisine |  ``` Optional ```  ``` DefaultValue ```  | The cuisine(s) of the recipes. One or more (comma separated) of the following: african, chinese, japanese, korean, vietnamese, thai, indian, british, irish, french, italian, mexican, spanish, middle eastern, jewish, american, cajun, southern, greek, german, nordic, eastern european, caribbean, or latin american. |
| diet |  ``` Optional ```  ``` DefaultValue ```  | The diet to which the recipes must be compliant. Possible values are: pescetarian, lacto vegetarian, ovo vegetarian, vegan, and vegetarian. |
| excludeIngredients |  ``` Optional ```  ``` DefaultValue ```  | An comma-separated list of ingredients or ingredient types that must not be contained in the recipes. |
| intolerances |  ``` Optional ```  ``` DefaultValue ```  | A comma-separated list of intolerances. All found recipes must not have ingredients that could cause problems for people with one of the given tolerances. Possible values are: dairy, egg, gluten, peanut, sesame, seafood, shellfish, soy, sulfite, tree nut, and wheat. |
| limitLicense |  ``` Optional ```  ``` DefaultValue ```  | Whether the recipes should have an open license that allows for displaying with proper attribution. |
| number |  ``` Optional ```  ``` DefaultValue ```  | The number of results to return (between 0 and 100). |
| offset |  ``` Optional ```  ``` DefaultValue ```  | The number of results to skip (between 0 and 900). |
| type |  ``` Optional ```  ``` DefaultValue ```  | The type of the recipes. One of the following: main course, side dish, dessert, appetizer, salad, bread, breakfast, soup, beverage, sauce, or drink. |
| queryParameters | ``` Optional ``` | Additional optional query parameters are supported by this method |


#### Example Usage

```java
String query = "burger";
String cuisine = "italian";
String diet = "vegetarian";
String excludeIngredients = "coconut";
String intolerances = "egg, gluten";
Boolean limitLicense = false;
Integer number = 10;
Integer offset = 0;
String type = "main course";
// key-value map for optional query parameters
Map<String, Object> queryParams = new LinkedHashMap<String, Object>();
// Invoking the API call with sample inputs
client.searchRecipesAsync(query, cuisine, diet, excludeIngredients, intolerances, limitLicense, number, offset, type, queryParams, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="find_by_nutrients_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.findByNutrientsAsync") findByNutrientsAsync

> *Tags:*  ``` Skips Authentication ``` 

> Find a set of recipes that adhere to the given nutrient limits. All the found recipes will have macro nutrients within the calories, protein, fat, and carbohydrate limits.


```java
void findByNutrientsAsync(
        final Integer maxcalories,
        final Integer maxcarbs,
        final Integer maxfat,
        final Integer maxprotein,
        final Integer mincalories,
        final Integer minCarbs,
        final Integer minfat,
        final Integer minProtein,
        Map<String, Object> queryParameters,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| maxcalories |  ``` Optional ```  ``` DefaultValue ```  | The maximum number of calories the recipe can have. |
| maxcarbs |  ``` Optional ```  ``` DefaultValue ```  | The maximum number of carbohydrates in grams the recipe can have. |
| maxfat |  ``` Optional ```  ``` DefaultValue ```  | The maximum number of fat in grams the recipe can have. |
| maxprotein |  ``` Optional ```  ``` DefaultValue ```  | The maximum number of protein in grams the recipe can have. |
| mincalories |  ``` Optional ```  ``` DefaultValue ```  | The minimum number of calories the recipe must have. |
| minCarbs |  ``` Optional ```  ``` DefaultValue ```  | The minimum number of carbohydrates in grams the recipe must have. |
| minfat |  ``` Optional ```  ``` DefaultValue ```  | The minimum number of fat in grams the recipe must have. |
| minProtein |  ``` Optional ```  ``` DefaultValue ```  | The minimum number of protein in grams the recipe must have. |
| queryParameters | ``` Optional ``` | Additional optional query parameters are supported by this method |


#### Example Usage

```java
Integer maxcalories = 1500;
Integer maxcarbs = 100;
Integer maxfat = 100;
Integer maxprotein = 100;
Integer mincalories = 0;
Integer minCarbs = 0;
Integer minfat = 0;
Integer minProtein = 0;
// key-value map for optional query parameters
Map<String, Object> queryParams = new LinkedHashMap<String, Object>();
// Invoking the API call with sample inputs
client.findByNutrientsAsync(maxcalories, maxcarbs, maxfat, maxprotein, mincalories, minCarbs, minfat, minProtein, queryParams, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="find_by_ingredients_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.findByIngredientsAsync") findByIngredientsAsync

> *Tags:*  ``` Skips Authentication ``` 

> Find recipes that use as many of the given ingredients as possible and have as little as possible missing ingredients. This is a whats in your fridge API endpoint.


```java
void findByIngredientsAsync(
        final String ingredients,
        final Boolean limitLicense,
        final Integer number,
        final Integer ranking,
        Map<String, Object> queryParameters,
        final APICallBack<List<FindByIngredientsModel>> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| ingredients |  ``` Required ```  ``` DefaultValue ```  | A comma-separated list of ingredients that the recipes should contain. |
| limitLicense |  ``` Optional ```  ``` DefaultValue ```  | Whether to only show recipes with an attribution license. |
| number |  ``` Optional ```  ``` DefaultValue ```  | The maximal number of recipes to return (default = 5). |
| ranking |  ``` Optional ```  ``` DefaultValue ```  | Whether to maximize used ingredients (1) or minimize missing ingredients (2) first. |
| queryParameters | ``` Optional ``` | Additional optional query parameters are supported by this method |


#### Example Usage

```java
String ingredients = "apples,flour,sugar";
Boolean limitLicense = false;
Integer number = 5;
Integer ranking = 1;
// key-value map for optional query parameters
Map<String, Object> queryParams = new LinkedHashMap<String, Object>();
// Invoking the API call with sample inputs
client.findByIngredientsAsync(ingredients, limitLicense, number, ranking, queryParams, new APICallBack<List<FindByIngredientsModel>>() {
    public void onSuccess(HttpContext context, List<FindByIngredientsModel> response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


#### <a name="create_recipe_card_async"></a>![Method: ](https://apidocs.io/img/method.png "com.mashape.p.spoonacularrecipefoodnutritionv1.controllers.APIController.createRecipeCardAsync") createRecipeCardAsync

> *Tags:*  ``` Skips Authentication ``` 

> Create a recipe card given a recipe.


```java
void createRecipeCardAsync(
        final String backgroundImage,
        final InputStream image,
        final String ingredients,
        final String instructions,
        final String mask,
        final int readyInMinutes,
        final int servings,
        final String title,
        final String author,
        final String backgroundColor,
        final String fontColor,
        final String source,
        Map<String, Object> fieldParameters,
        final APICallBack<DynamicResponse> callBack)
```

#### Parameters

| Parameter | Tags | Description |
|-----------|------|-------------|
| backgroundImage |  ``` Required ```  ``` DefaultValue ```  | The background image ("none","background1", or "background2"). |
| image |  ``` Required ```  ``` DefaultValue ```  | The binary image of the recipe as jpg. |
| ingredients |  ``` Required ```  ``` DefaultValue ```  | The ingredient list of the recipe, one ingredient per line. |
| instructions |  ``` Required ```  ``` DefaultValue ```  | The instructions to make the recipe. One step per line. |
| mask |  ``` Required ```  ``` DefaultValue ```  | The mask to put over the recipe image ("ellipseMask", "diamondMask", "diamondMask", "starMask", "heartMask", "potMask", "fishMask"). |
| readyInMinutes |  ``` Required ```  ``` DefaultValue ```  | The number of minutes it takes to get the recipe on the table. |
| servings |  ``` Required ```  ``` DefaultValue ```  | The number of servings that you can make from the ingredients. |
| title |  ``` Required ```  ``` DefaultValue ```  | The title of the recipe. |
| author |  ``` Optional ```  ``` DefaultValue ```  | The author of the recipe. |
| backgroundColor |  ``` Optional ```  ``` DefaultValue ```  | The background color on the recipe card as a hex-string. |
| fontColor |  ``` Optional ```  ``` DefaultValue ```  | The font color on the recipe card as a hex-string. |
| source |  ``` Optional ```  ``` DefaultValue ```  | The source of the recipe. |
| fieldParameters | ``` Optional ``` | Additional optional form parameters are supported by this method |


#### Example Usage

```java
String backgroundImage = "background1";
InputStream image = new InputStream("PathToFile");
String ingredients = "2 cups of green beans";
String instructions = "cook the beans";
String mask = "ellipseMask";
int readyInMinutes = 60;
int servings = 2;
String title = "Pork tenderloin with green beans";
String author = "Emily Henderson";
String backgroundColor = "#ffffff";
String fontColor = "#333333";
String source = "spoonacular.com";
// key-value map for optional form parameters
Map<String, Object> formParams = new LinkedHashMap<String, Object>();
// Invoking the API call with sample inputs
client.createRecipeCardAsync(backgroundImage, image, ingredients, instructions, mask, readyInMinutes, servings, title, author, backgroundColor, fontColor, source, formParams, new APICallBack<DynamicResponse>() {
    public void onSuccess(HttpContext context, DynamicResponse response) {
        // TODO success callback handler
    }
    public void onFailure(HttpContext context, Throwable error) {
        // TODO failure callback handler
    }
});

```


[Back to List of Controllers](#list_of_controllers)



