package com.asterism.fresk.presenter;

import com.asterism.fresk.contract.IBookCityContract;

public class BookCityPresenter extends NewBasePresenter<IBookCityContract.View,IBookCityContract.Model>
        implements IBookCityContract.Presenter {
    @Override
    protected IBookCityContract.Model setModel() {
        return null;
    }
}
