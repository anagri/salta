SaltaWeb::Application.routes.draw do
  resources :user_sessions, :only => [:new, :create, :destroy]
  resources :users, :only => [:new, :create]
  resources :groups
  match 'logout' => 'user_sessions#destroy', :as => :logout
  match 'login' => 'user_sessions#new', :as => :login
  root :to => 'groups#index'
end
