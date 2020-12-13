/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.controller;

import mic.dermitzakis.HairSalon.view.FxmlController;

/**
 *
 * @author mderm
 */
public abstract class AbstractTableViewController implements FxmlController {
    
    @Override
    public void initialize() { 
        assignTableColumns();
        insertDataIntoTable();
        setEventHandler();
        restoreState();
    }

    protected abstract void assignTableColumns();

    protected abstract void insertDataIntoTable();

    protected abstract void setEventHandler();

    protected abstract void restoreState();

}
