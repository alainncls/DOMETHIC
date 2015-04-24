package fr.epf.medfile.daos;

import android.content.Context;
import fr.epf.medfile.daos.news.NewsService;
import fr.epf.medfile.daos.news.NewsServiceImpl;
import fr.epf.medfile.daos.patient.PatientService;
import fr.epf.medfile.daos.patient.PatientServiceImpl;
import fr.epf.medfile.daos.ressource.RessourceService;
import fr.epf.medfile.daos.ressource.RessourceServiceImpl;
import fr.epf.medfile.daos.user.UserService;
import fr.epf.medfile.daos.user.UserServiceImpl;

public class ServiceProvider {

    private static ServiceProvider instance;

    private PatientService ps;
    private UserService us;
    private NewsService ns;
    private RessourceService rs;

    private ServiceProvider(Context context) {
	ps = new PatientServiceImpl(context);
	us = new UserServiceImpl(context);
	ns = new NewsServiceImpl(context);
	rs = new RessourceServiceImpl(context);
    }

    public static PatientService getPInstance(Context context) {
	if (instance == null) {
	    instance = new ServiceProvider(context);
	}
	return instance.ps;
    }

    public static UserService getUInstance(Context context) {
	if (instance == null) {
	    instance = new ServiceProvider(context);
	}
	return instance.us;
    }

    public static NewsService getNInstance(Context context) {
	if (instance == null) {
	    instance = new ServiceProvider(context);
	}
	return instance.ns;
    }

    public static RessourceService getRInstance(Context context) {
	if (instance == null) {
	    instance = new ServiceProvider(context);
	}
	return instance.rs;
    }
}
