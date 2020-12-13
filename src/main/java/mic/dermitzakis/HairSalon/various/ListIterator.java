/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.various;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 * @param <T>
 */
@Component
@Scope("prototype")
public class ListIterator<T> {
    List<T> list;
    private int index;

    public ListIterator(List<T> list) {
        this.list = list;
        this.index = -1;
    }

    public void reset(){
        index = -1;
    }
    
    public Optional<T> first(){
        if (!list.isEmpty()) {
            index = 0;
            return Optional.of(list.get(index));
        } else return Optional.empty();
    }
    
    public Optional<T> last(){
        if (!list.isEmpty()){
            index = list.size() - 1;
            return Optional.of(list.get(index));
        }  else return Optional.empty();
    }
    
    public Optional<T> previous(){
        if (hasPrevious())
            return Optional.of(list.get(--index));
        else return Optional.empty();
    }
    
    public Optional<T> next(){
        if (hasNext())
            return Optional.of(list.get(++index));
        else return Optional.empty();
    }
    
    public boolean hasPrevious(){
        return index > 0;
    }
    
    public boolean hasNext(){
        if (list.isEmpty()) return false;
        return index < list.size() - 1;
    }      //     0           1
           //     1           2
    public void add(T t){
        list.add(t);
        index++;
    }
    
    public void remove(T t){
        list.remove(t);
    }

    public void remove(int i){
        list.remove(i);
    }

    public void remove(){
        list.remove(index);
    }

    public void set(T t) {
        list.set(index, t);
    }
    
    public T atCursor(){
        return list.get(index);
    }
            
    public int cursor(){
        return index;
    }

    public boolean isExist(T t) {
        return list.stream().anyMatch(isEqual(t));
    }

    private Predicate<? super T> isEqual(T t) {
        Predicate<? super T> predicate = (T t1) -> t.equals(t1);
        return predicate;
    }
        
}
