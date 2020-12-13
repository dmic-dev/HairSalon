/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.mapper;

/**
 *
 * @author mderm
 * @param <T>
 * @param <C>
 */
@FunctionalInterface
public interface Mapper<T> {
    
    public <S extends Object> T extract(S object);
    
}
