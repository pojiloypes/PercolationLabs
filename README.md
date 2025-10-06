

# PercolationLabs

> Репозиторий для лабораторных работ по курсу «Перколяционные системы»

---

## 📁 Структура проекта

```
PercolationLabs/
├── src/
│   ├── _CustomDataStructures/
│   │   ├── DisjointSet.java
│   │   └── Point2D.java
│   ├── lab1/
│   │   ├── KnotPercolationModel.java
│   │   └── Main.java
│   ├── lab2/
│   │   ├── Main.java
│   │   ├── RibPercolationModel.java
│   │   ├── PercolationModel2D.java
│   │   └── ScaledCirclesView.java
│   └── lab3/
│       ├── ClusterModel.java
│       └── Main.java
└── README.md
```

## 🧪 Описание лабораторных работ

### Lab 1: Узловая перколяционная модель
- **KnotPercolationModel.java** — генерация и моделирование узловой сетки.
- **Main.java** — запуск моделирования, ввод параметров через консоль.

### Lab 2: Перколяционная модель связей и 2D-модели
- **RibPercolationModel.java** — генерация сетки связей.
- **Main.java** — запуск моделирования связей.
- **PercolationModel2D.java** — основные алгоритмы 2D-перколяции.
- **ScaledCirclesView.java** — визуализация результатов (Swing).

### Lab 3: Кластерный анализ
- **ClusterModel.java** — вычисление кластеров на основе узловой сетки.
- **Main.java** — запуск кластерного анализа, взаимодействие с пользователем.

### _CustomDataStructures
- **DisjointSet.java** — реализация структуры непересекающихся множеств (DSU/Union-Find).
- **Point2D.java** — вспомогательный класс для работы с точками.

---

## 🚀 Быстрый старт

1. **Компиляция:**
   Откройте терминал в корне проекта и выполните:
   ```sh
   javac -d out src/_CustomDataStructures/*.java src/lab1/*.java src/lab2/*.java src/lab3/*.java
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

