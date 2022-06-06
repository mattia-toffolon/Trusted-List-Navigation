<!-- IF YOU SEE THIS LINE PLEASE OPEN README.html INSTEAD OF THIS FILE -->
# Trusted-List-Navigation
Group: ProseccoCoding
Members:
- Mattia Toffolon   <br />
- Francesco Stella  <br />
- Filippo D'Emilio  <br />
- Alberto Guerrini  <br />

## Links to some deliverable files
- Complete **Javadoc documentation** can be found [here](https://mattia-toffolon.github.io/Trusted-List-Navigation/javadoc/index.html).
- **System Design document** can be found [here](https://mattia-toffolon.github.io/Trusted-List-Navigation/docs/ProgettoTLN-SystemDesign.pdf).
- Complete **Testing report document** can be found [here](https://mattia-toffolon.github.io/Trusted-List-Navigation/docs/ProgettoTLN-TestReport.pdf).
- **JUnit test report** can be found [here](https://mattia-toffolon.github.io/Trusted-List-Navigation/surfire/surefire-report.html).

## How to run Trusted List Navigation application
Here it is described in few steps how to run this application and how to properly use it.
### Prerequisites
1. Your environment must have a **Java VM** installed, if it haven't it you can download it from [here](https://www.oracle.com/java/technologies/downloads/).
2. Your system must have an internet connection (:memo: **Note:** be aware that the response time of this application depends by the connection data-rate) 
3. You must have this repository on your local sistem. There are two ways to achieve that:
    - Clone it on your system moving in your preferred local folder and entering in your terminal `$ git clone https://github.com/mattia-toffolon/Trusted-List-Navigation.git` (you must have git installed).
    - Or download it as zip from the green button on the top right of this page, unzip and open the unzipped folder (no additional software required).
 <br />

### Launching the application
Note that the .jar file is cross-platform compiled, so it would run in all common platforms (Windows, Linux, MacOS).
1. Firstly you have to move in the base directory (where is located the `.jar` file) and open a terminal on it.
2. Then you have to type `$ java -jar Trusted-List-Navigation.jar` to launch the Java application. 
<br /> :memo: **Note:** In case of not working internet connection or EU trusted data's server fault can be displayed an error before the application launch. In this case you should try again from point 2.
4. Finally, if no error occurs the application should display home view. <br />
    <img src="https://user-images.githubusercontent.com/96696061/172061538-b92ebc08-e02b-41c7-9e96-1c323d21055e.png"  height="300" /> <br />


### View countries and service types lists
**1. View EU country list** <br />
    - In the home view you can find the button <ins>*See the list of the EU Countries*</ins> and press it. <br />
    - The application should display the complete list of EU countries and a button to return to the homepage. <br /><br />
**2. View service types list** <br />
    - As for countries, in the home view you can find the button <ins>*See the list of the Trust-Service types*</ins> and press it. <br />
    - The application should display the complete list of EU service types and a button to return to the homepage. <br /> <br />
 <br />

### Search for trusted-services
1. In the home view you can find the button <ins>*Start a new query*</ins> and press it. <br />
2. The application should display the first selection page: **Country selection**: <br />
    <img src="https://user-images.githubusercontent.com/96696061/172064000-3c9ae6d4-28f7-4c86-9d8d-b5b0f7895f78.png"  height="300" /> <br />
    Here you can select all your preferred countries or all countries using *Select all* checkbox and press <ins>*Next*</ins> to go on trough the query. <br />
    (:warning: **Warning:** you must select at least one country).
    
3. The application should display the first selection page: **Provider selection**: <br />
    <img src="https://user-images.githubusercontent.com/96696061/172064033-8c3758e9-4028-4b38-a623-d23e593e749b.png"  height="300" /> <br />
    Here you can select all your preferred providers or all providers using *Select all* checkbox and press <ins>*Next*</ins> to go on trough the query. <br />
    If you want to change the previous selection you can press the <ins>*Back*</ins> button. <br />
    (:warning: **Warning:** you must select at least one provider).
    
4. The application should display the second selection page: **Service type selection**: <br />
    <img src="https://user-images.githubusercontent.com/96696061/172064064-d947cecf-de89-4c6b-9537-61492b5db941.png"  height="300" /> <br /> 
    Here you can select all your preferred service types or all types using *Select all* checkbox and press <ins>*Next*</ins> to go on trough the query. <br />
    If you want to change the previous selection you can press the <ins>*Back*</ins> button. <br />
    (:warning: **Warning:** you must select at least one service type).

5. The application should display the third selection page: **Service status selection**: <br />
    <img src="https://user-images.githubusercontent.com/96696061/172064169-1b342464-666b-4e66-906e-71eed9f7e5e8.png"  height="300" /> <br />
    Here you can select all your preferred service status or all status using *Select all* checkbox and press <ins>*Start*</ins> to display the results. <br />
    If you want to change the previous selection you can press the <ins>*Back*</ins> button. <br />
    (:warning: **Warning:** you must select at least one service status).
    
6. Finally the application should display the **result page**: <br />
    <img src="https://user-images.githubusercontent.com/96696061/172064312-bf8997b9-e055-4470-97b7-7b2d8d295c1c.png"  height="300" /> <br />
    Here are listed all the services that meet all the selections that you made. <br />
    Now you can go back to one of the selection page with the <ins>*Back*</ins> button or end the query and display the homepage pressing the <ins>*Home*</ins> button.
    <br /> 
7. :warning: **Warning:** In some cases, like when you try to go to the next selection page without having no checkboxes selected, an error message like this is
    displayed: <br />
    <img src="https://user-images.githubusercontent.com/48312863/171997619-98eaa0a7-3c6a-4f78-b91f-15dce627cde5.png"  height="100" /> <br />
    To continue the query parameter selection **you must close** this **alert window**, otherwise it is not possible to interact with the main window. <br />
 <br /> <br />

# Some concepts about the implementation of TLN application
This application interacts with the [EU Trusted List API](https://esignature.ec.europa.eu/efda/swagger-ui.html) to retrieve [EU Trust Services](https://esignature.ec.europa.eu/efda/home/#/screen/home) data and provide useful search and display functions to **analize EU trusted services data**.<br />
The API interaction is done over **http protocol** at application launch time. One call is made to **retrieve country code and name** (using */tl-browser/api/v1/search/countries_list* service), and the other is made to retrieve all the providers and services for every country (using
*/tl-browser/api/v1/search/tsp_list* service).<br />
The calls to API services retrieve data in **json format**, and this data is **stored as JSONObject** and parsed in Country, Provider, Service, ServiceType objects. only if it is needed. In this way only two http calls are done so their time overhead is reduced at a minimum point.  <br/>
Moreover **only needed countries are parsed** in specific objects so the time and space complexity is reduced at the lowest level. It is important to note that a country with its complete data is **parsed** to specific objects **only the first time**, then it's kept in case of future use.<br />
Country complete data is parsed in a *Country object* that contains a **collection of Providers objects** and every Provider contains its **Service objects** in a **multimap** using as **key** the **ServiceType**. By doing that we can obtain a minimal time complexity to retrieve services by type.<br />
Every Service object contains attributes for type, status and other minor informations.<br />
When the user starts a new search a **Query object** is created. This Query object **contains all the selected countries objects** (with all their data), and **for each selection** the user makes, it **computes and stores a subset of informations** from the initial set of countries that match the user selection. At the end of all the selections only the services that meet the user choices will remain, and they can be shown.<br /><br />

# External libraries and functionality used in TNL application
- **org.openjfx** libraries to build the graphical user interface
- **org.json** library to handle json data retrieved from the API services
-  **com.google.guava** libraries used only for the MultiMap implementation for storing services in provider class
-  **org.junit** library used for unit class testing
-  For the dependencies management, running configuration and software lifecycle management this project uses [maven](https://maven.apache.org/).
-  For the unique cross-platform .jar building it has been used [maven shade plugin](https://maven.apache.org/plugins/maven-shade-plugin/).
