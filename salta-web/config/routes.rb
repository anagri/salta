SaltaWeb::Application.routes.draw do
  resource :user_session, :only => [:new, :create, :destroy]
  resource :user, :only => [:new, :create]
  resources :groups
  match 'logout' => 'user_sessions#destroy', :as => :logout
  match 'login' => 'user_sessions#new', :as => :login
  root :to => 'groups#index'
end
