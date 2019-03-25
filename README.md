# Project “Cruise company”

## Variant 22. System "Cruise company"
	The Company has several Ships. Ships have passenger capacity, route, number of ports visited, duration of one cruise, personnel. The client chooses and pays the cruise. Selects guided tours upon arrival at the port for an additional fee. The Administrator of the Ship indicates bonuses for passengers, taking into account the class of the ticket (pool, gym, cinema, beauty salons ...).

### Installation
**Installation option 1**

	1.	On the page https://github.com/Dean4iq/Cruise_Company select the option “Clone or download” and download the ZIP archive of the project.
	2.	After downloading the archive, extract the root folder in the archive anywhere in the local storage.
	3.	Ensure that at least 8 versions of JDK and Maven 3 are installed on the device.
	4.	For convenience, you should use the integrated development tool (IDE), for example, Intellij Idea, with which you can manage the project.
	
**Installation option 2**

	1.	Ensure that at least 8 versions of JDK and Maven 3 are installed on the device.
	2.	On the page https://github.com/Dean4iq/Cruise_Company select the option “Clone or download” and select Open in Desktop.
	3.	To successfully clone a project, you need to preload GitHub Desktop and log in with your account.
	4.	In the window that opens, select the path in the local storage where the project will be stored. After that, click Clone.
	5.	All repository files will be uploaded to the directory that was selected in the previous step.
	6.	For convenience, you should use the integrated development tool (IDE), for example, Intellij Idea, with which you can manage the project.
	
### Starting project
**Startup option 1**

	1.	Using Intellij Idea (hereinafter IDE) to open this project.
	2.	In the IDE in the upper right corner, click “Add configuration”.
	3.	In the window that opens, select + in the upper left corner, then select Maven in the drop-down list.
	4.	In the right part of the window, set the following parameters:
		•	The Name can be any;
		•	In the Working directory, the absolute location of the project should be specified;
		•	In the command line, enter “tomcat7:run”.
	5.	At the bottom, in the Before launch section, click on + and select Run Maven Goal.
	6.	In the window that opens, enter “clean” in the “Command line” field. Click OK in all dialog boxes.
	7.	Run the project using the Shift + F10 key combination or click on the green triangle from the top right
	8.	After running, go to the web browser at http://localhost:14949/company
	
**Startup option 2**

	1.	Run the command line and go to the project root folder in it.
	2.	Enter the command “mvn clean tomcat7:run”.
	3.	If the mvn command is not detected, then the path to the installed Maven should be specified in Path environment variables, then try to start the project again.


# Проект “Круизная Компания”

## Вариант 8. Система Круизная Компания. 
	Система Круизная Компания. У Компании имеется несколько Кораблей. У Кораблей есть пассажиро-вместимость, маршрут, количество посещаемых портов, длительность одного круиза, персонал. Клиент выбирает и оплачивает круиз. Выбирает Экскурсии по прибытии в порт за дополнительную плату. Администратор Корабля указывает бонусы для пассажиров, учитывая класс билета (бассейн, спорт зал, кинозал, косметические салоны...).

### Установка
**Вариант установки 1**

	1.	На странице https://github.com/Dean4iq/Cruise_Company выбрать опцию “Clone or download” и скачать ZIP архив проекта.
	2.	После загрузки архива извлечь корневую папку в архиве в любом месте локального хранилища.
	3.	Убедиться, что на устройстве установлены JDK минимум 8 версии и Maven 3.
	4.	Для удобства следует воспользоваться интегрированным средством разработки (IDE), например, Intellij Idea, с помощью которого можно управлять проектом.
	
**Вариант установки 2**

	1.	Убедиться, что на устройстве установлены JDK минимум 8 версии и Maven 3.
	2.	На странице https://github.com/Dean4iq/Cruise_Company выбрать опцию “Clone or download” и выбрать Open in Desktop.
	3.	Для успешного клонирования проекта, необходимо заранее загрузить GitHub Desktop и войти под своей учетной записью.
	4.	В открывшемся окне выбрать путь в локальном хранилище, где будет храниться проект. После этого, нажать Clone.
	5.	Все файлы репозитория будут загружены в директорию, которая была выбрана на предыдущем этапе.
	6.	Для удобства следует воспользоваться интегрированным средством разработки (IDE), например, Intellij Idea, с помощью которого можно управлять проектом.
	
### Запуск
**Вариант запуска 1**

	1.	С помощью Intellij Idea (далее IDE) открыть данный проект.
	2.	В IDE в верхнем правом углу нажать “Add configuration”.
	3.	В открывшемся окне выбрать в левом верхнем углу +, после чего в выпадающем списке следует выбрать Maven.
	4.	В правой части окна задать следующие параметры:
		•	Name можно задать любое;
		•	В Working directory должен быть задан абсолютный путь размещения проекта;
		•	В Command line ввести “tomcat7:run”.
	5.	Внизу, в разделе Before launch, нажать на + и выбрать Run Maven Goal.
	6.	В открывшемся окне в поле “Command line” ввести “clean”. Нажать ОК во всех диалоговых окнах.
	7.	Запустить проект с помощью комбинации клавиш Shift+F10 или нажать на зеленый треугольник сверху справа
	8.	После загрузки перейти в веб-браузере по адресу http://localhost:14949/company
	
**Вариант запуска 2**

	1.	Запустить командную строку и в ней перейти в корневую папку проекта.
	2.	Ввести команду “mvn clean tomcat7:run”.
	3.	Если команды mvn не обнаружено, то следует указать в переменные среды Path путь к установленному Maven, после чего попытаться запустить проект снова.
