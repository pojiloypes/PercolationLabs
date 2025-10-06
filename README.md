
# PercolationLabs

> Репозиторий для лабораторных работ по курсу «Перколяционные системы»

---

## 📁 Структура проекта

```
PercolationLabs/
├── src/
│   ├── lab1/
│   │   ├── KnotPercolationModel.java
│   │   └── Main.java
│   ├── lab2/
│   │   ├── Main.java
│   │   ├── RibPercolationModel.java
│   │   └── Task3/
│   │       ├── PercolationModel2D.java
│   │       ├── Point2D.java
│   │       └── ScaledCirclesView.java
|	|── lab3/
|	|	├── ClusterModel.java
|	|	├── DisjointSet.java
|	|	└── Main.java
└── README.md
```

## 🧪 Описание лабораторных работ

### Lab 1: Узловая перколяционная модель
- **KnotPercolationModel.java** — генерация и моделирование узловой сетки.
- **Main.java** — запуск моделирования, ввод параметров через консоль.

### Lab 2: Перколяционная модель связей и 2D-модели
- **RibPercolationModel.java** — генерация сетки связей.
- **Main.java** — запуск моделирования связей.
- **Task3/** — моделирование и визуализация 2D-перколяции:
	- **PercolationModel2D.java** — основные алгоритмы.
	- **Point2D.java** — вспомогательный класс для точек.
	- **ScaledCirclesView.java** — визуализация результатов (Swing).

### Lab 3: Кластерный анализ и объединение множеств
- **ClusterModel.java** — вычисление кластеров на основе узловой сетки.
- **DisjointSet.java** — реализация структуры непересекающихся множеств (DSU/Union-Find).
- **Main.java** — запуск кластерного анализа, взаимодействие с пользователем.

---

## 🚀 Быстрый старт

1. **Компиляция:**
	 Откройте терминал в корне проекта и выполните:
	 ```sh
	 javac -d out src/lab1/*.java src/lab2/*.java src/lab2/Task3/*.java src/lab3/*.java
	 ```
2. **Запуск:**
	 - Для Lab 1:
		 ```sh
		 java -cp out lab1.Main
		 ```
	 - Для Lab 2:
		 ```sh
		 java -cp out lab2.Main
		 ```
	 - Для Lab 3:
		 ```sh
		 java -cp out lab3.Main
		 ```

---

## 📝 Примечания
- Для корректной работы используйте JDK 8 или выше.
- Ввод параметров осуществляется через консоль.
- Для визуализации 2D-моделей требуется поддержка Swing (обычно есть в стандартной JDK).

