package org.wahlzeit.model.flower;

import org.wahlzeit.model.*;
import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "Abstract Factory Pattern",
        participants = {
                "AbstractFactory", "ConcreteFactory"
        }
)

public class FlowerPhotoFactory extends PhotoFactory {
    /**
     * Hidden singleton instance; needs to be initialized from the outside.
     */
    private static FlowerPhotoFactory instance = null;

    /**
     * Public singleton access method.
     */
    public static synchronized FlowerPhotoFactory getInstance() {
        if (instance == null) {
            SysLog.logSysInfo("setting generic FlowerPhotoFactory");
            setInstance(new FlowerPhotoFactory());
        }

        return instance;
    }

    /**
     * Method to set the singleton instance of PhotoFactory.
     */
    protected static synchronized void setInstance(FlowerPhotoFactory flowerphotoFactory) {
        if (instance != null) {
            throw new IllegalStateException("attempt to initialize FlowerPhotoFactory twice");
        }

        instance = flowerphotoFactory;
    }

    /**
     * Hidden singleton instance; needs to be initialized from the outside.
     */
    public static void initialize() {
        getInstance(); // drops result due to getInstance() side-effects
    }

    /**
     *
     */
    protected FlowerPhotoFactory() {
        // do nothing
    }

    /**
     * @methodtype factory
     */
    public FlowerPhoto createPhoto() {
        return new FlowerPhoto();
    }

    /**
     *
     */
    public FlowerPhoto createPhoto(PhotoId id) {
        return new FlowerPhoto(id);
    }

    /**
     *
     */
    public FlowerPhoto createPhoto(ResultSet rs) throws SQLException {
        return new FlowerPhoto(rs);
    }

    /**
     *
     */
    public PhotoFilter createPhotoFilter() {
        return new PhotoFilter();
    }

    /**
     *
     */
    public PhotoTagCollector createPhotoTagCollector() {
        return new PhotoTagCollector();
    }


}
