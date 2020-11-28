package com.example.gblibs.rxlearn

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function4
import io.reactivex.rxjava3.functions.Function5
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Operators {

    fun exec() {
        Consumer(Producer()).exec()
    }

    class Producer {

        fun randomResultOperation(): Boolean {
            Thread.sleep(1500)
            return listOf(true, false, true)[Random.nextInt(2)]
        }

        fun createJust() = Observable.just("1", "2", "3", "4")
        fun createFromIterable() = Observable.fromIterable(listOf("1", "2", "3", "3"))
        fun createRange() = Observable.range(10, 10)
        fun createInterval() = Observable.interval(100, TimeUnit.MILLISECONDS).map { "1_$it" }
        fun createInterval2() = Observable.interval(150, TimeUnit.MILLISECONDS).map { "2_$it" }
        fun createTimer() = Observable.timer(500, TimeUnit.MILLISECONDS)
        fun createFromCallable() = Observable.fromCallable {
            val result = randomResultOperation()
            return@fromCallable "Result of operation $result"
        }

        fun create() = Observable.create<String> { emitter ->
            //val result = randomResultOperation()
            emitter.onError(RuntimeException("Failure"))
//            if (result) {
//                emitter.onNext("value")
//                emitter.onComplete()
//            } else {
//                emitter.onError(RuntimeException("Failure"))
//            }
        }

    }

    class Consumer(val producer: Producer) {
        fun exec() {
            //execJust()
            //execFromIterable()
            //execRange()
            //execInterval()
            //execFromCallable()
            //execMap()
            //execFilter()
            //execDistinct()
            //execMerge()
            //execDebounce()
            //execZip()
            //execFlatMap()
            //execSwitchMap()
        }

        fun execJust() {
            producer.createJust().subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable?) {
                    println("onSubscribe")
                }

                override fun onNext(s: String?) {
                    println("onNext: $s")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })
        }

        fun execFromIterable() {
            producer.createJust().map {
                it + "asd"
            }.subscribe {
                println("onNext: $it")
            }
        }

        fun execRange() {
            producer.createRange().subscribe {
                println("onNext: $it")
            }
        }

        fun execInterval() {
            producer.createInterval().subscribe {
                println("onNext: $it")
            }
        }

        fun execFromCallable() {
            producer.createFromCallable().subscribe {
                println("onNext: $it")
            }
        }

        fun execMap() {
            producer.createJust()
                .map { it + it }
                .subscribe {
                    println("onNext: $it")
                }
        }

        fun execFilter() {
            producer.createJust()
                .filter { it.toInt() > 2 }
                .subscribe {
                    println("onNext: $it")
                }
        }

        fun execDistinct() {
            producer.createJust()
                .distinct()
                .subscribe {
                    println("onNext: $it")
                }
        }

        fun execTake() {
            producer.createJust()
                .take(2)
                .subscribe {
                    println("onNext: $it")
                }
        }

        fun execSkip() {
            producer.createJust()
                .skip(2)
                .subscribe {
                    println("onNext: $it")
                }
        }

        fun execMerge() {
            producer.createJust()
                .concatWith(producer.createInterval2())
                .subscribe {
                    println("onNext: $it")
                }
        }

        fun execDebounce() {
            Observable.create<String> { emitter ->
                emitter.onNext("1")
                Thread.sleep(100)
                emitter.onNext("2")
                Thread.sleep(100)
                emitter.onNext("3")
                Thread.sleep(100)
                emitter.onNext("4")
                Thread.sleep(300)
                emitter.onNext("5")
            }.debounce(200, TimeUnit.MILLISECONDS)
                .subscribe {
                    println("onNext: $it")
                }
        }

        fun execZip() {
            val observableError = producer.create().delay(2, TimeUnit.SECONDS).doOnNext { println("error emitted") }.doOnError { "error from create" }
                .retry(3)
                .onErrorResumeNext {
                    return@onErrorResumeNext Observable.just("replacement")
                }
            val observable1 = Observable.just("1", "11").delay(1, TimeUnit.SECONDS).doOnNext { println("1 emitted") }
            val observable2 = Observable.just("2", "22").delay(2, TimeUnit.SECONDS).doOnNext { println("2 emitted") }
            val observable3 = Observable.just("3").delay(3, TimeUnit.SECONDS).doOnNext { println("3 emitted") }
            val observable4 = Observable.just("4").delay(4, TimeUnit.SECONDS).doOnNext { println("4 emitted") }

            Observable.zip(
                observableError,
                observable1,
                observable2,
                observable3,
                observable4,
                Function5<String, String, String, String, String, List<String>> { error, t1, t2, t3, t4 ->
                    return@Function5 listOf(error, t1, t2, t3, t4)
                }).subscribe({
                println(it.toString())
            }, {
                println("onError: ${it.message}")
            })
        }

        fun execFlatMap() {
            val testScheduler = TestScheduler()
            producer.createJust()
                .flatMap {
                    val delay = Random.nextInt(10).toLong()
                    return@flatMap Observable.just(it + "x").delay(delay, TimeUnit.SECONDS, testScheduler).doOnNext { println("$it emitted") }
                }
                .toList()
                .subscribe { s ->
                    println("onNext: $s")
                }

            testScheduler.advanceTimeBy(1, TimeUnit.MINUTES)
        }
    }

}