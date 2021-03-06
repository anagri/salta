SaltaWeb::Application.routes.draw do
  resources :user_sessions, :only => [:new, :create, :destroy]
  match '/user_sessions/status' => 'user_sessions#session_status', :as => :user_session_status
  resources :users, :only => [:new, :create] do
    resources :profiles
  end
  resources :groups do
    post 'remove_membership'
    post 'add_membership'
    post 'invite'
  end
  match '/android/groups' => 'android#groups'
  match '/android/groups/:group_id/members' => 'android#members'
  match '/android/members/:member_id' => 'android#member'
  match 'logout' => 'user_sessions#destroy', :as => :logout
  match 'login' => 'user_sessions#new', :as => :login
  match 'invite/:token' => 'invites#show', :as => :invite
  match '/dashboard' => 'dashboard#index', :as => :dashboard
  root :to => 'dashboard#index'
end
