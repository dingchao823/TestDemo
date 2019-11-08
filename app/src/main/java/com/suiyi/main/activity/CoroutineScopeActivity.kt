package com.suiyi.main.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.suiyi.main.constants.Path
import kotlinx.coroutines.*

@Route(path = Path.COROUTINES_SCOPE_ACTIVITY_PATH)
class CoroutineScopeActivity : AppCompatActivity() {

    val singleDispatcher = newSingleThreadContext("Single")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        test10()
    }

    /**
     * 协程 launch 模式不会导致主线程阻塞
     */
    private fun test1() {
        val name = "方法"
        val count = 1
        GlobalScope.launch {
            var position = 0
            while (true) {
                Log.e("dc", "method $name - $count step $position")
                delay(500)
                position++
            }
        }
        Thread.sleep(1000)
    }

    /**
     * 两个协程顺序执行，协程 jobB 等 jobA 之后执行，需要设置为 Lazy 模式，即要使用的时候协程才执行
     */
    private fun test2() {
        val jobA = GlobalScope.launch(Dispatchers.Default, CoroutineStart.LAZY) {
            for (index in 1..10) {
                Log.e("dc", "jobA start")
            }
        }
        val jobB = GlobalScope.launch(Dispatchers.Default, CoroutineStart.LAZY) {
            jobA.join()
            Log.e("dc", "jobB start")
        }
        jobB.start()
    }

    /**
     * job A 执行到一半的时候取消，交由 jobB 执行
     */
    private fun test3() {
        val jobA = GlobalScope.launch(Dispatchers.Default, CoroutineStart.LAZY) {
            for (index in 1..10) {
                Log.e("dc", "jobA start $index")
                if (index == 5) {
                    return@launch
                }
            }
        }
        val jobB = GlobalScope.launch(Dispatchers.Default, CoroutineStart.LAZY) {
            jobA.join()
            Log.e("dc", "jobB start")
        }
        jobB.start()
    }

    /**
     * 协程不需要锁，对于同一共享变量的操作很方便，因为协程是顺序执行的
     */
    private fun test4() {
        var shareParam = ""
        val jobA = GlobalScope.launch(Dispatchers.Default, CoroutineStart.LAZY) {
            while (true) {
                delay(1000)
                shareParam = "test ---> change to 1"
                Log.e("dc", "shareParam = $shareParam")
            }
        }
        val jobB = GlobalScope.launch(Dispatchers.Default, CoroutineStart.LAZY) {
            while (true) {
                delay(1000)
                shareParam = "test ---> change to 2"
                Log.e("dc", "shareParam = $shareParam")
            }
        }
        val jobC = GlobalScope.launch(Dispatchers.Default, CoroutineStart.LAZY) {
            while (true) {
                delay(1000)
                shareParam = "test ---> change to 3"
                Log.e("dc", "shareParam = $shareParam")
            }
        }
        // 如果不按顺序这样start，使用launch其他启动模式会在已创建就执行，
        // 一开始会输出132，但是后面都是顺序执行123
        jobA.start()
        jobB.start()
        jobC.start()
    }

    /**
     * 与 launch 不同，async 函数返回值为 deferred，launch 是 job ，也就是协程本身
     * async 不会阻塞当前线程，但是会阻塞当前协程，可以用于网络请求
     */
    private fun test5() {
        GlobalScope.launch(Dispatchers.Unconfined) {
            val deferred = GlobalScope.async {
                delay(1000L)
                Log.e("dc", "This is async ")
                return@async "taonce"
            }
            Log.e("dc", "协程 other start")
            val result = deferred.await()
            Log.e("dc", "async result is $result")
            Log.e("dc", "协程 other end ")
        }
        Log.e("dc", "主线程位于协程之后的代码执行，时间:  ${System.currentTimeMillis()}")
    }

    /**
     * runBlocking 会阻塞当前线程，跟 launch 不一样
     */
    private fun test6() {
        runBlocking {
            Log.e("dc", "线程阻塞开始....")
            delay(1000)
            Log.e("dc", "线程阻塞结束....")
        }
        Log.e("dc", "主线程开始....")
    }

    /**
     * 例子中是谁写在前面谁先执行，但是实际结果是主线程先执行，然后才是协程任务(疑问点？)
     */
    private fun test7() {
        GlobalScope.launch {
            for (index in 1..6) {
                Log.e("dc", "协程任务 $index")
            }
        }
        for (index in 1..6) {
            Log.e("dc", "主线程任务 $index")
        }
    }

    /**
     * suspend 关键字会将整个协程挂起，而不是针对一个函数，协程挂起时不会再向下执行，直到挂起结束，因为协程是顺序
     * 执行的，因此就算协程挂起结束，也不会立即运行，会等到协程下个空闲时间才会执行，不存在CPU抢时间这种情况
     *
     * 按照道理来说本来就应该顺序执行方法，但是在每个方法里面是有延时的，如果不用suspend就不能使用delay，不信可以
     * 把delay去掉
     */
    private fun test8(){

        GlobalScope.launch {

            test8_1()
            test8_2()
            Log.e("dc", "test8 launch")
        }

    }

    private suspend fun test8_1(){
        delay(1000)
        Log.e("dc", "test8_1 execute ${Thread.currentThread().name}")
    }

    private suspend fun test8_2(){
        delay(1000)
        Log.e("dc", "test8_2 execute ${Thread.currentThread().name}")
    }

    /**
     * 协程挂起之后在哪个线程运行呢？哪个线程恢复的协程就在哪个线程中运行，并不是你原来在哪个线程，挂起结束又回到了
     * 那个线程。比如协程运行在线程A中，挂起，线程B唤醒了协程，所以现在协程切换到了线程B中。
     *
     * 但是在实际操作中，发现 test8_1 实际唤醒他的是 kotlinx.coroutines.DefaultExecutor，而不是主线程
     */
    private fun test9(){

        GlobalScope.launch(Dispatchers.Main) {

            Log.e("dc", "协程运行在 ${Thread.currentThread().name}")

            GlobalScope.async(Dispatchers.Unconfined) {
                return@async test8_1()
            }.await()

            GlobalScope.async(Dispatchers.Unconfined) {
                return@async test8_2()
            }.await()

        }

        Log.e("dc", "主线程运行在 ${Thread.currentThread().name}")

    }

    /**
     * delay 和 yield 都可以挂起协程，区别在于 delay 是挂起协程并经过执行时间恢复协程
     *
     */
    private fun test10(){
        val job = GlobalScope.launch {
            val job = GlobalScope.launch {
                launch {
                    withContext(singleDispatcher) {
                        repeat(3) {
                            Log.e("dc", "Task1")
                            Thread.sleep(1000)
//                            yield()
                        }
                    }
                }

                launch {
                    withContext(singleDispatcher) {
                        repeat(3) {
                            Log.e("dc", "Task2")
                            Thread.sleep(1000)
//                            yield()
                        }
                    }
                }
            }
        }
    }

}