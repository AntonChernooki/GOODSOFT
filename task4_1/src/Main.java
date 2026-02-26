//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    task1();
    task2();
    System.out.println("ВЫВОД ПО ПРЕДЫДУЩИМ ДВУМ ЗАДАНИЯМ");
    System.out.println("Set не хранит дубликаты , что для строк работает автоматически а для обьектов пользовательского класса нужно переопределять equals() и hashCode(). При работе со списками вставка и удаление элементов в обычном for приводит к сдвигу индексов , что влечет ошибки ");
    task3();
    task4();
    task5();


}

public void task1() {
    System.out.println("ЗАДАНИЕ 1 ");
    Set<String> strings = new HashSet<>();

    strings.add("один");
    strings.add("два");
    strings.add("три");
    strings.add("четыре");
    strings.add("пять");

    System.out.println("Вывод элементов из множество строк используя foreach");
    for (String str : strings) {
        System.out.print(str + " ");
    }
    System.out.println(" ");
    System.out.println(" ");

    strings.add("один");

    Iterator<String> iterator = strings.iterator();
    System.out.println("Вывод элементов из множество строк с помощью итератора(дублирующий элемент не добавился )");

    while (iterator.hasNext()) {
        System.out.print(iterator.next() + " ");
    }
    System.out.println(" ");
    System.out.println(" ");

    Set<UserClass> userClassSet = new HashSet<>();
    UserClass userClass1 = new UserClass(1, "один");
    UserClass userClass2 = new UserClass(2, "два");
    UserClass userClass3 = new UserClass(3, "три");
    UserClass userClass4 = new UserClass(4, "четыре");
    UserClass userClass5 = new UserClass(5, "пять");
    userClassSet.add(userClass1);
    userClassSet.add(userClass2);
    userClassSet.add(userClass3);
    userClassSet.add(userClass4);
    userClassSet.add(userClass5);

    System.out.println("Создал множество из экземпляров пользовательского класса и вывел их в понятном виде");
    for (UserClass userclass : userClassSet) {
        System.out.println(userclass);
    }
    System.out.println(" ");

    System.out.println("добавил 6 дублирующий обьект этого класса (он не добавился)");
    UserClass userClass6 = new UserClass(5, "пять");
    userClassSet.add(userClass6);
    for (UserClass userclass : userClassSet) {
        System.out.println(userclass);
    }
    System.out.println(" ");
}

public void task2() {
    System.out.println("ЗАДАНИЕ 2 ");
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    System.out.println("Вывел элементы списка при помощи foreach");
    for (Integer integer : list) {
        System.out.print(integer + " ");
    }
    System.out.println(" ");
    System.out.println(" ");


    System.out.println("Вывел элементы списка при помощи for при этом во время вывода, 3 элемента вставил на 2 позицию ещё один новый элемент.");
    for (int i = 0; i < list.size(); i++) {

        System.out.print(list.get(i) + " ");
        if (i == 2) {
            list.add(1, 10);
        }

    }
    System.out.println(" ");
    System.out.println(" ");
    System.out.println("Вывел список обычным циклом for");
    for (int i = 0; i < list.size(); i++) {

        System.out.print(list.get(i) + " ");

    }

    System.out.println(" ");
    System.out.println(" ");
    System.out.println("Вывел список обычным циклом for, во время вывода  3 элемента удалил элемент со 2 позиции.");
    for (int i = 0; i < list.size(); i++) {

        System.out.print(list.get(i) + " ");
        if (i == 2) {
            list.remove(1);
        }
    }
    System.out.println(" ");
    System.out.println(" ");
    System.out.println("Вывел список обычным циклом for");
    for (int i = 0; i < list.size(); i++) {

        System.out.print(list.get(i) + " ");

    }
    System.out.println(" ");
    System.out.println(" ");


}

public void task3() {
    System.out.println("ЗАДАНИЕ 3 ");
    Map<String, Integer> map = new HashMap<>();
    map.put("один", 1);
    map.put("два", 2);
    map.put("три", 3);
    System.out.println("Вывод map с помощью преобразование ее в entrySet");
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        System.out.println(entry.getKey() + " " + entry.getValue());
    }

    map.put("три", 5);
    System.out.println(" ");
    System.out.println("Вывод после замены c помощью преобразование ее в KeySet");
    for (String key : map.keySet()) {
        System.out.println(key + " " + map.get(key));
    }
    Map<UserClass, Integer> userMap = new HashMap<>();
    userMap.put(new UserClass(5, "пять"), 55);
    userMap.put(new UserClass(6, "шесть"), 66);
    userMap.put(new UserClass(7, "семь"), 77);
    System.out.println(" ");
    System.out.println("Вывод содержимого новой map в которой ключи это обьекты пользовательского класса");
    for (Map.Entry<UserClass, Integer> entry : userMap.entrySet()) {
        System.out.println(entry.getKey() + "        " + entry.getValue());
    }

    userMap.put(new UserClass(7, "семь"), 88);
    System.out.println(" ");
    System.out.println("Вывод содержимого новой map в которой я добавил элемент с дублирующим ключом");
    for (Map.Entry<UserClass, Integer> entry : userMap.entrySet()) {
        System.out.println(entry.getKey() + "        " + entry.getValue());
    }
    System.out.println(" ");
}

public void task4() {
    System.out.println("ЗАДАНИЕ 4 ");
    Set<String> stringSet = new TreeSet<>();
    stringSet.add("ббббб");
    stringSet.add("ааааа");
    stringSet.add("ггггг");
    stringSet.add("ууууу");
    stringSet.add("ддддд");
    stringSet.add("ввввв");
    System.out.println("Вывод отсортированного множества строк ");
    for (String str : stringSet) {
        System.out.println(str);
    }
    System.out.println(" ");
    Set<UserClass> userSet = new TreeSet<>();
    userSet.add(new UserClass(5, "пять"));
    userSet.add(new UserClass(2, "два"));
    userSet.add(new UserClass(4, "четыре"));
    userSet.add(new UserClass(3, "три"));
    userSet.add(new UserClass(1, "один"));
    System.out.println("Вывод отсортированного пользовательского  множества  ");
    for (UserClass str : userSet) {
        System.out.println(str);
    }
    System.out.println(" ");
}

public void task5() {
    System.out.println("ЗАДАНИЕ 5 ");
    Map<String, Integer> stringIntegerMap = new TreeMap<>();
    stringIntegerMap.put("ббббб", 2);
    stringIntegerMap.put("ааааа", 1);
    stringIntegerMap.put("ггггг", 4);
    stringIntegerMap.put("ууууу", 10);
    stringIntegerMap.put("ддддд", 5);
    stringIntegerMap.put("ввввв", 3);
    System.out.println("Вывод отсортированного map где ключи-строки ");
    for (Map.Entry<String, Integer> str : stringIntegerMap.entrySet()) {
        System.out.println(str);
    }
    System.out.println(" ");
    Map<UserClass, Integer> userClassIntegerMap = new TreeMap<>();
    userClassIntegerMap.put(new UserClass(5, "пять"), 5);
    userClassIntegerMap.put(new UserClass(2, "два"), 2);
    userClassIntegerMap.put(new UserClass(4, "четыре"), 4);
    userClassIntegerMap.put(new UserClass(3, "три"), 3);
    userClassIntegerMap.put(new UserClass(1, "один"), 1);
    System.out.println("Вывод отсортированной map где ключи- обьекты пользовательского  множества  ");
    for (Map.Entry<UserClass, Integer> str : userClassIntegerMap.entrySet()) {
        System.out.println(str);
    }
    System.out.println(" ");
}
