package com.proyecto.estructuras.models;

// import com.proyecto.estructuras.models.AVLHashMap;
// import java.util.*;

// public class AVLHashMapBenchmark {
//     public static void main(String[] args) {
//         int[] sizes = {1_000, 10_000, 100_000, 1_000_000, 10_000_000};

//         for (int size : sizes) {
//             System.out.println("Tamaño: " + size);
//             AVLHashMap<Integer, Integer> map = new AVLHashMap<>();

//             // insert
//             long start = System.nanoTime();
//             for (int i = 0; i < size; i++) {
//                 map.insert(i, i);
//             }
//             long end = System.nanoTime();
//             System.out.printf("insert: %.2f ms%n", (end - start) / 1e6);

//             // getNode
//             start = System.nanoTime();
//             for (int i = 0; i < size; i += 1000) {
//                 map.getNode(i);
//             }
//             end = System.nanoTime();
//             System.out.printf("getNode: %.2f ms%n", (end - start) / 1e6);

//             // replace
//             start = System.nanoTime();
//             for (int i = 0; i < size; i += 1000) {
//                 map.replace(i, i + 1);
//             }
//             end = System.nanoTime();
//             System.out.printf("replace: %.2f ms%n", (end - start) / 1e6);

//             // getAllValues
//             start = System.nanoTime();
//             map.getAllValues();
//             end = System.nanoTime();
//             System.out.printf("getAllValues: %.2f ms%n", (end - start) / 1e6);

//             // removeFromHashMap
//             start = System.nanoTime();
//             for (int i = 0; i < size; i += 1000) {
//                 map.removeFromHashMap(i);
//             }
//             end = System.nanoTime();
//             System.out.printf("removeFromHashMap: %.2f ms%n", (end - start) / 1e6);

//             // removeFromAVL
//             List<Integer> toRemove = new ArrayList<>();
//             for (int i = 500; i < 1500; i++) toRemove.add(i);
//             start = System.nanoTime();
//             for (Integer key : toRemove) {
//                 var node = map.getNode(key);
//                 if (node != null) map.removeFromAVL(node);
//             }
//             end = System.nanoTime();
//             System.out.printf("removeFromAVL: %.2f ms%n", (end - start) / 1e6);

//             // resize (solo para size pequeño)
//             if (size == 10_000) {
//                 start = System.nanoTime();
//                 map.resize();
//                 end = System.nanoTime();
//                 System.out.printf("resize: %.2f ms%n", (end - start) / 1e6);
//             }

//             System.out.println("-------------------------");
//         }
//     }
// }
