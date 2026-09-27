package lab;

import java.time.Instant;
import java.time.Duration;
import java.util.Random;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    
    public interface Operacion {
        void apply();
    }

    public static class Metodo {
        String name;
        Operacion operacion;

        public Metodo(String name, Operacion operacion) {
            this.name = name;
            this.operacion = operacion;
        }
    }

    public interface GeneradorMetodos {
        Metodo[] generar(int n, Random rand);
    }

    static void ejecutarBenchmark(String titulo, int start, int end, int intentos,
                                   Random rand, GeneradorMetodos generadorPorTamano, 
                                   GeneradorMetodos generadorPorIntento) throws IOException {
        System.out.println("\nMidiendo tiempos de " + titulo);

        Map<Integer, Map<String, Double>> resultadosPorTamano = new LinkedHashMap<>();
        List<String> ordenMetodos = new ArrayList<>();
        Metodo[] operacionesPorTamano, operacionesPorIntento;

        for (int n = start; n <= end; n *= 10) {
            System.out.printf("n = %d \n", n);
            Map<String, Long> totales = new LinkedHashMap<>();

            operacionesPorTamano = generadorPorTamano.generar(n, rand);
            for(Metodo m : operacionesPorTamano) if(!ordenMetodos.contains(m.name)) ordenMetodos.add(m.name);
            for(int rep = 0; rep < intentos; rep++){
                for (Metodo m : operacionesPorTamano) {
                    Instant inicio = Instant.now();
                    m.operacion.apply();
                    Instant fin = Instant.now();
                    long ns = Duration.between(inicio, fin).toNanos();
                    totales.merge(m.name, ns, Long::sum);
                }

                operacionesPorIntento = generadorPorIntento.generar(n, rand);
                for (Metodo m : operacionesPorIntento) {
                    if(!ordenMetodos.contains(m.name)) ordenMetodos.add(m.name);
                    Instant inicio = Instant.now();
                    m.operacion.apply();
                    Instant fin = Instant.now();
                    long ns = Duration.between(inicio, fin).toNanos();
                    totales.merge(m.name, ns, Long::sum);
                }
            }

            Map<String, Double> promedios = new LinkedHashMap<>();
            for (String nombreMetodo : ordenMetodos) {
                double promedio = totales.get(nombreMetodo) / (double) intentos;
                promedios.put(nombreMetodo, promedio);
                //System.out.printf("n=%d | %s: %.2f ns \n", n, nombreMetodo, promedio);
            }
            resultadosPorTamano.put(n, promedios);
        }

        try (PrintWriter csv = new PrintWriter(new FileWriter(titulo + ".csv"))) {
            StringBuilder encabezado = new StringBuilder("Tamaño");
            for (String nombreMetodo : ordenMetodos) {
                encabezado.append(",").append(nombreMetodo);
            }
            csv.println(encabezado);

            for (Map.Entry<Integer, Map<String, Double>> fila : resultadosPorTamano.entrySet()) {
                StringBuilder linea = new StringBuilder(String.valueOf(fila.getKey()));
                for (String nombreMetodo : ordenMetodos) {
                    linea.append(",").append(String.format("%.2f", fila.getValue().get(nombreMetodo)));
                }
                csv.println(linea);
            }
        }

        System.out.println("csv de " + titulo + " guardado");
    }

    public static void main(String[] args) throws IOException {
        final int start = 10;
        final int end = 1_000_000;
        final int INTENTOS = 5;
        Random rand = new Random();

        /* ============================================================
           1. SinglyLinkedListNoTail
           ============================================================ */
        ejecutarBenchmark("SinglyLinkedListNoTail", start, end, INTENTOS, rand, 
            (n, r) -> {
                SinglyLinkedListNoTail<Integer> lista = new SinglyLinkedListNoTail<>();
                for (int i = 0; i < n; i++) lista.pushFront(r.nextInt());
                return new Metodo[] {
                    new Metodo("PushFront", () -> lista.pushFront(r.nextInt())),
                    new Metodo("PushBack",  () -> lista.pushBack(r.nextInt())),
                    new Metodo("PopFront",  () -> lista.popFront()),
                    new Metodo("PopBack",   () -> lista.popBack())
                };
            },
            (n, r) -> {
                SinglyLinkedListNoTail<Integer> lista = new SinglyLinkedListNoTail<>();
                int[] valoresLista = new int[n];
                for(int i = 0; i < n; i++){
                    int valor = r.nextInt();
                    lista.pushFront(valor);
                    valoresLista[i] = valor;
                }
                int valorObjetivo = valoresLista[r.nextInt(n)];
                var refNodo = lista.find(valorObjetivo);
                return new Metodo[] {
                    new Metodo("Find",      () -> lista.find(valorObjetivo)),
                    new Metodo("AddBefore", () -> lista.addBefore(refNodo, r.nextInt())),
                    new Metodo("AddAfter",  () -> lista.addAfter(refNodo, r.nextInt())),
                    new Metodo("Erase",     () -> lista.erase(refNodo))
                };
            }
        );

        /* ============================================================
           2. SinglyLinkedListWithTail
           ============================================================ */
        ejecutarBenchmark("SinglyLinkedListWithTail", start, end, INTENTOS, rand, 
            (n, r) -> {
                SinglyLinkedListWithTail<Integer> lista = new SinglyLinkedListWithTail<>();
                for (int i = 0; i < n; i++) lista.pushFront(r.nextInt());
                return new Metodo[] {
                    new Metodo("PushFront", () -> lista.pushFront(r.nextInt())),
                    new Metodo("PushBack",  () -> lista.pushBack(r.nextInt())),
                    new Metodo("PopFront",  () -> lista.popFront()),
                    new Metodo("PopBack",   () -> lista.popBack())
                };
            },
            (n, r) -> {
                SinglyLinkedListWithTail<Integer> lista = new SinglyLinkedListWithTail<>();
                int[] valoresLista = new int[n];
                for(int i = 0; i < n; i++){
                    int valor = r.nextInt();
                    lista.pushFront(valor);
                    valoresLista[i] = valor;
                }
                int valorObjetivo = valoresLista[r.nextInt(n)];
                var refNodo = lista.find(valorObjetivo);
                return new Metodo[] {
                    new Metodo("Find",      () -> lista.find(valorObjetivo)),
                    new Metodo("AddBefore", () -> lista.addBefore(refNodo, r.nextInt())),
                    new Metodo("AddAfter",  () -> lista.addAfter(refNodo, r.nextInt())),
                    new Metodo("Erase",     () -> lista.erase(refNodo))
                };
            }
        );

        /* ============================================================
           3. DoublyLinkedListNoTail
           ============================================================ */
        ejecutarBenchmark("DoublyLinkedListNoTail", start, end, INTENTOS, rand, 
            (n, r) -> {
                DoublyLinkedListNoTail<Integer> lista = new DoublyLinkedListNoTail<>();
                for (int i = 0; i < n; i++) lista.pushFront(r.nextInt());
                return new Metodo[] {
                    new Metodo("PushFront", () -> lista.pushFront(r.nextInt())),
                    new Metodo("PushBack",  () -> lista.pushBack(r.nextInt())),
                    new Metodo("PopFront",  () -> lista.popFront()),
                    new Metodo("PopBack",   () -> lista.popBack())
                };
            },
            (n, r) -> {
                DoublyLinkedListNoTail<Integer> lista = new DoublyLinkedListNoTail<>();
                int[] valoresLista = new int[n];
                for(int i = 0; i < n; i++){
                    int valor = r.nextInt();
                    lista.pushFront(valor);
                    valoresLista[i] = valor;
                }
                int valorObjetivo = valoresLista[r.nextInt(n)];
                var refNodo = lista.find(valorObjetivo);
                return new Metodo[] {
                    new Metodo("Find",      () -> lista.find(valorObjetivo)),
                    new Metodo("AddBefore", () -> lista.addBefore(refNodo, r.nextInt())),
                    new Metodo("AddAfter",  () -> lista.addAfter(refNodo, r.nextInt())),
                    new Metodo("Erase",     () -> lista.erase(refNodo))
                };
            }
        );

        /* ============================================================
           4. DoublyLinkedListWithTail
           ============================================================ */
        ejecutarBenchmark("DoublyLinkedListWithTail", start, end, INTENTOS, rand, 
            (n, r) -> {
                DoublyLinkedListWithTail<Integer> lista = new DoublyLinkedListWithTail<>();
                for (int i = 0; i < n; i++) lista.pushFront(r.nextInt());
                return new Metodo[] {
                    new Metodo("PushFront", () -> lista.pushFront(r.nextInt())),
                    new Metodo("PushBack",  () -> lista.pushBack(r.nextInt())),
                    new Metodo("PopFront",  () -> lista.popFront()),
                    new Metodo("PopBack",   () -> lista.popBack())
                };
            },
            (n, r) -> {
                DoublyLinkedListWithTail<Integer> lista = new DoublyLinkedListWithTail<>();
                int[] valoresLista = new int[n];
                for(int i = 0; i < n; i++){
                    int valor = r.nextInt();
                    lista.pushFront(valor);
                    valoresLista[i] = valor;
                }
                int valorObjetivo = valoresLista[r.nextInt(n)];
                var refNodo = lista.find(valorObjetivo);
                return new Metodo[] {
                    new Metodo("Find",      () -> lista.find(valorObjetivo)),
                    new Metodo("AddBefore", () -> lista.addBefore(refNodo, r.nextInt())),
                    new Metodo("AddAfter",  () -> lista.addAfter(refNodo, r.nextInt())),
                    new Metodo("Erase",     () -> lista.erase(refNodo))
                };
            }
        );

        /* ============================================================
           5. MyStackArray
           ============================================================ */
        ejecutarBenchmark("MyStackArray", start, end, INTENTOS, rand, 
            (n, r) -> {
                MyStack<Integer> stack = new MyStackArray<>(1);
                for(int i = 0; i < n; i++) stack.push(r.nextInt());
                return new Metodo[] {
                    new Metodo("Push",    () -> stack.push(r.nextInt())),
                    new Metodo("Pop",     () -> stack.pop()),
                    new Metodo("Peek",    () -> stack.peek()),
                    new Metodo("IsEmpty", () -> stack.isEmpty()),
                    new Metodo("Size",    () -> stack.size())
                };
            },
            (n, r) -> {
                MyStack<Integer> stack = new MyStackArray<>(1);
                int[] valoresStack = new int[n];
                for (int i = 0; i < n; i++) {
                    int valor = r.nextInt();
                    stack.push(valor);
                    valoresStack[i] = valor;
                }
                int valorObjetivo = valoresStack[r.nextInt(n)];

                return new Metodo[] {
                    new Metodo("Delete",  () -> stack.delete(valorObjetivo))
                };
            }
        );

        /* ============================================================
           6. MyQueueArray
           ============================================================ */
        ejecutarBenchmark("MyQueueArray", start, end, INTENTOS, rand, 
            (n, r) -> {
                MyQueue<Integer> queue = new MyQueueArray<>(1);
                for(int i = 0; i < n; i++) queue.enqueue(r.nextInt());
                return new Metodo[] {
                    new Metodo("Push",    () -> queue.enqueue(r.nextInt())),
                    new Metodo("Pop",     () -> queue.dequeue()),
                    new Metodo("Peek",    () -> queue.front()),
                    new Metodo("IsEmpty", () -> queue.isEmpty()),
                    new Metodo("Size",    () -> queue.size())
                };
            },
            (n, r) -> {
                MyQueue<Integer> queue = new MyQueueArray<>(1);
                int[] valoresQueue = new int[n];
                for (int i = 0; i < n; i++) {
                    int valor = r.nextInt();
                    queue.enqueue(valor);
                    valoresQueue[i] = valor;
                }
                int valorObjetivo = valoresQueue[r.nextInt(n)];

                return new Metodo[] {
                    new Metodo("Delete",  () -> queue.delete(valorObjetivo))
                };
            }
        );

        System.out.println("\nArchivo csv generado para cada estructura\nFin");
    }
}