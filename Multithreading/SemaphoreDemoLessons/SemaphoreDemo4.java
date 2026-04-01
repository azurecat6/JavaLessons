package Multithreading.SemaphoreDemoLessons;

import java.util.concurrent.*;

public class SemaphoreDemo4 {
    public static final int CHARGERS = 4;
    public static final int SCOOTERS = 15;
    static Semaphore semaphore = new Semaphore(CHARGERS, true);
    static ConcurrentHashMap<Integer, ChargerState> chargerStates = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < SCOOTERS; i++) {
            new Thread(new Scooter(i)).start();
        }
    }

    static class ChargerState {
        long startTime;
        boolean beingEvicted;

        public ChargerState(long startTime, boolean beingEvicted) {
            this.startTime = startTime;
            this.beingEvicted = beingEvicted;
        }
    }

    static class Scooter implements Runnable {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int currentChargerId = -1;
        public final int id;

        public Scooter(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    boolean acquiredImmediately = semaphore.tryAcquire(0, TimeUnit.MILLISECONDS);

                    if (!acquiredImmediately) {
                        long now = System.currentTimeMillis();
                        for (int i = 0; i < CHARGERS; i++) {
                            ChargerState state = chargerStates.get(i);
                            if (state != null && now - state.startTime > 8000) {
                                chargerStates.compute(i, (k, old) -> {
                                    if (old != null && now - old.startTime > 8000) {
                                        old.beingEvicted = true;
                                        return old;
                                    }
                                    return old;
                                });
                                break;
                            }
                        }
                    }

                    semaphore.acquire(); 

                    currentChargerId = findFreeChargerSlot();

                    long start = System.currentTimeMillis();
                    chargerStates.compute(currentChargerId, (k, old) -> new ChargerState(start, false));

                    int needed = random.nextInt(4000, 12000);
                    boolean wasEvicted = false;

                    while (System.currentTimeMillis() - start < needed) {
                        Thread.sleep(300);

                        ChargerState st = chargerStates.get(currentChargerId);
                        if (st != null && st.beingEvicted) {
                            wasEvicted = true;
                            break;
                        }
                    }

                    chargerStates.computeIfPresent(currentChargerId, (k, old) -> null);
                    semaphore.release();

                    if (!wasEvicted) {
                        System.out.println("Самокат " + id + " заряжен");
                        return;
                    }

                    System.out.println("Самокат " + id + " выгнали, пробую снова");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        private int findFreeChargerSlot() {
            for (int i = 0; i < CHARGERS; i++) {
                if (!chargerStates.containsKey(i)) {
                    return i;
                }
            }
            throw new IllegalStateException("Нет свободной зарядки, хотя acquire прошёл");
        }
    }
}



//TODO
// **Задача 3.2**
// Есть 4 зарядки для электросамокатов.
// Приезжают 15 самокатов (потоков).
// Каждый самокат заряжается 4–12 секунд.
// Но!
// Если зарядка занята больше 8 секунд — самокат имеет право «отобрать» её у того, кто уже заряжается (т.е. заставить его освободить зарядку).
// Как это можно реализовать с помощью семафоров?
// (подсказка: один из способов — использовать дополнительный семафор + флаги)