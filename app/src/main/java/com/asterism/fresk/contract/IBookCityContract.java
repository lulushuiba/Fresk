package com.asterism.fresk.contract;

public interface IBookCityContract {
    interface View extends INewBaseContract.View, IBaseContract.View {

    }

    interface Presenter extends INewBaseContract.Presenter<IBookCityContract.View, IBookCityContract.Model> {

    }

    interface Model extends IBaseContract.Model {

    }
}
