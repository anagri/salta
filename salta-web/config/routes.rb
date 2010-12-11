SaltaWeb::Application.routes.draw do
  resource :user_session, :only => [:new, :create, :destroy]
  resource :user, :only => [:new, :create]

  root :to => 'dashboard#index'
end
