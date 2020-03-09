package com.githubtrending.core.component;

import com.githubtrending.page.landing.LandingActivity;
import com.githubtrending.core.module.AppProviderModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kevin.adhitama on 2020-03-09.
 */

@Singleton
@Component(modules = {AppProviderModule.class})
public interface ApplicationComponent {

    void inject(LandingActivity landingActivity);

}
