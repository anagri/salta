SaltaWeb::Application.routes.draw do
  resources :user_sessions, :only => [:new, :create, :destroy]
  resources :users, :only => [:new, :create]
  resources :groups do
    post 'remove_membership'
    post 'add_membership'
    post 'invite'
  end
  match 'logout' => 'user_sessions#destroy', :as => :logout
  match 'login' => 'user_sessions#new', :as => :login
  match 'invite/:token' => 'invites#show', :as => :invite
  root :to => 'groups#index'
end
