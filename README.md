# HomeWorkAnd

### Домашнее задание к занятию «1.1. Android Studio, SDK, эмулятор и первое приложение»

<details close><summary> Задача Code Like a Pro</summary>
    <br>
✔️ При выполнении задачи используется **GitHub Actions** для сборки приложения в ***apk-файл*** (и последующего тестирования) при каждом пуше.

Проект выводит на экран текстовую надпись **NMedia!** вместо **Hello, World**
> При создании проекта использовались следующие настройки:
>
>    applicationId: ru.netology.nmedia
>    versionName: 1.0
>    minSdk (минимальная версия Android): 23 (Android 6.0)
</details>  

### Домашнее задание к занятию «1.2. Ресурсы, View и ViewGroup»

<details close><summary> Задача Launcher Icon</summary>
  <br>

Заменена стандартная иконка приложения Android на кастомную - [логотип Нетологии](https://github.com/netology-code/and2-homeworks/blob/master/02_resources/assets/netology.svg)

![](https://raw.githubusercontent.com/netology-code/and2-homeworks/4c90eaafc1bb9566cabaa487c1442d8b647ea85e/02_resources/assets/netology.svg)

Для создания иконок используется Image Asset Studio, который входит в состав Android Studio и позволяет выбрать изображение и сам разместит необходимые файлы в каталогах res/mipmap.

➡️ Начиная с Android 8.0, применяется подход адаптивных иконок запуска, которые разделяют подложку иконки - **background** и непосредственно **foreground** - часть (чаще всего логотип), позволяя в зависимости от устройства менять форму подложки.

<details close>
    
<summary> ❓ Если интересно - 💡 можно прочесть</summary>
<br>
Иконка указывается в манифесте: (атрибуты android:icon и android:roundIcon)

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="ru.netology.nmedia">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>

Эти значения ведут на файлы mipmap/ic_launcher и (mipmap/ic_launcher_round) соответственно. В зависимости от версии платформы это будут либо сгенерированные изображения в формате png, либо xml, в которых стоят ссылки на **foreground** и **background** ресурсы.
</details>
  </details>

<details close><summary> Задача Translations</summary>
  <br>
Добавление перевода на русский язык (для поддержания мультиязычности).
  
  Переводиться должны:
  
*   Название приложения (пусть на русском будет **"НМедиа"**)
*   Текст (пусть на русском будет ***"НМедиа!"***)
</details>

### Домашнее задание к занятию «1.3. Constraint Layout»

<details close><summary> Задача Layout</summary>
    <br>
Вёрстка для получения приложения следующего вида :arrow_heading_down:

![](https://github.com/netology-code/and2-homeworks/blob/master/03_constraint_layout/pic/layout.png?raw=true)

Реализована разметка в соответствии с заданием (при увеличении чисел изменяется величина строки). Все иконки взяты из стандартного набора.

</details>

### Домашнее задание к занятию «2.1. Обработка событий в Android»

<details close><summary> Like, Share</summary>
    <br>
    
Добавлен следующий функционал приложения:

* При клике на like меняется не только картинка, но и число рядом с ней: like - увеличивается на +1, dislike - уменьшается на -1
* При клике на share увеличиваться число рядом (10 раз нажали на share - +10)
* Добавлена логика с тысячами и миллионами: если количество лайков, share или просмотров перевалило за 999, то должно отображается 1K и т.д., а не 1000

:heavy_exclamation_mark::heavy_exclamation_mark::heavy_exclamation_mark: **Attention** :heavy_exclamation_mark::heavy_exclamation_mark::heavy_exclamation_mark:

    1.1К отображается по достижении 1100
    После 10К сотни перестают отображаться
    После 1M сотни тысяч отображаются в формате 1.3M
    Логика по расчёту и преобразованию вынесена как отдельный объект

</details>

### Домашнее задание к занятию «2.2. Архитектура: MVVM»

<details close><summary> Задача MVVM </summary>
    <br>
    
Проект переделан согласно архитектуре **MVVM**.

~~That's all~~ :hammer_and_wrench: ~~, but it's not easy~~ :trollface:

</details>

### Домашнее задание к занятию «2.3. Отображение списков: RecyclerView»

<details close><summary> Задача RecyclerView </summary>
    <br>

В проект добавлена реализацию отображения списков на базе RecyclerView и ListAdapter.

По аналогии с лекцией к *OnLikeListener*, добавлен *OnShareListener*.

</details>

### Домашнее задание к занятию «2.4. CRUD: списки, добавление, удаление, изменение»

<details close><summary> Задача Задача CRUD и отмена редактирования </summary>
    <br>

- В проект приложения добавлена реализация **CRUD**.

- Реализована отмена редактирования (по аналогии с *Telegram*)

![](https://github.com/netology-code/and2-homeworks/blob/master/07_crud/pic/cancel.png?raw=true)

Для этого с помощью ConstraintLayout сформирована соответствующую структура над полем ввода поста.
View объединены в [виртуальную группу](https://developer.android.com/reference/androidx/constraintlayout/widget/Group).

Во ViewModel выставляются нужные значения для сокрытия и отображения панели:

    group.visibility = View.GONE        // сокрытие
    group.visibility = View.VISIBLE     // отображение

</details>

</details>

### Домашнее задание к занятию «3.1. Material Design»

<details close><summary> Задача Кнопки </summary>
    <br>

Стилизованы кнопки **Like**, **Share**, **Menu**, а также **View** в виде *Button*, согласно документации на компоненты  :open_book:  [Buttons](https://material.io/develop/android/components/buttons).

Текст задан через атрибуты кнопки (кол-во лайков, шаринга, просмотров).

Создан и назначен кнопкам отдельный стиль ***styles.xml***.

</details>
