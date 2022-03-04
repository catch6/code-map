模板模式的定义是：模板方法模式在一个方法中定义一个算法骨架，并将某些步骤推迟到子类中实现。模板方法模式可以让子类在不改变算法整体结构的情况下，重新定义算法中的某些步骤。

这里的算法可以理解为我们的业务逻辑。

## 应用场景

- 有多个子类共有的方法，且逻辑相同。
- 重要的、复杂的方法，可以考虑作为模板方法。

## 模板模式在JDK中的应用

### InputStream

在 Java IO 类库中，有很多类的设计用到了模板模式，比如 InputStream、OutputStream、Reader、Writer。这里以 InputStream 说明。

InputStream 的 read() 函数是一个模板方法，定义了读取数据的整个流程，并且暴露了一个可以由子类来定制的抽象方法，只不过这个方法也被命名为read()，只是参数跟模板方法不同。

```java
public abstract class InputStream implements Closeable {
    //...省略其他代码...

    public int read(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }
        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte) c;
        int i = 1;
        try {
            for (; i < len; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte) c;
            }
        } catch (IOException ee) {
        }
        return i;
    }

    public abstract int read() throws IOException;

}

public class ByteArrayInputStream extends InputStream {
    //...省略其他代码...

    @Override
    public synchronized int read() {
        return (pos < count) ? (buf[pos++] & 0xff) : -1;
    }

}
```

### AbstractList

在 AbstractList 类中，addAll() 函数可以看作模板方法，add() 是子类需要重写的方法，尽管没有声明为 abstract 的，但函数实现直接抛出了
UnsupportedOperationException 异常。

```java
public boolean addAll(int index,Collection<?extends E> c){
        rangeCheckForAdd(index);
        boolean modified=false;
        for(E e:c){
        add(index++,e);
        modified=true;
        }
        return modified;
        }
public void add(int index,E element){
        throw new UnsupportedOperationException();
        }
```

同样的并发工具类中的 `AbstractQueuedSynchronizer` 也是应用了模板方法模式。了解了模板方法模式有助于我们理解j.u.c包中的很多并发工具类实现。

```java
public final void acquireShared(int arg){
        if(tryAcquireShared(arg)< 0)
        doAcquireShared(arg);
        }

protected int tryAcquireShared(int arg){
        throw new UnsupportedOperationException();
        }
```

## 类图

![类图](https://cdn.wenzuo.net/assets/202203041537181.png)

## 代码实现

AbstractClass

```java
public abstract class AbstractClass {

    public final void method() {
        System.out.println("do step 1");
        // ....
        m1();
        System.out.println("do step 2");
        // ....
        m2();
        System.out.println("do step 3");
        // ...
    }

    public abstract void m1();

    public abstract void m2();

}
```

SubClass

```java
public class SubClass extends AbstractClass {

    @Override
    public void m1() {
        System.out.println("m1");
    }

    @Override
    public void m2() {
        System.out.println("m2");
    }

}
```

Main

```java
public class Main {

    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        subClass.method();
    }

}
```

## 模板模式与回调的区别

回调是一种双向调用关系。A 类事先注册某个函数 F 到 B 类，A 类在调用 B 类的 P 函数的时候，B 类反过来调用 A 类注册给它的 F 函数。这里的 F 函数就是“回调函数”。A 调用 B，B
反过来又调用 A，这种调用机制就叫作“回调”。

从应用场景上来看，同步回调跟模板模式几乎一致。它们都是在一个大的算法骨架中，自由替换其中的某个步骤，起到代码复用和扩展的目的。而异步回调跟模板模式有较大差别，更像是观察者模式。

从代码实现上来看，回调和模板模式完全不同。回调基于组合关系来实现，把一个对象传递给另一个对象，是一种对象之间的关系；模板模式基于继承关系来实现，子类重写父类的抽象方法，是一种类之间的关系。

组合优于继承，回调相对于模板模式会更加灵活：

- 基于模板模式编写的子类，已经继承了一个父类，不再具有继承的能力。
- 回调可以使用匿名类来创建回调对象，可以不用事先定义类；而模板模式针对不同的实现都要定义不同的子类。
- 如果某个类中定义了多个模板方法，每个方法都有对应的抽象方法，那即便我们只用到其中的一个模板方法，子类也必须实现所有的抽象方法。而回调就更加灵活，我们只需要往用到的模板方法中注入回调对象即可。
