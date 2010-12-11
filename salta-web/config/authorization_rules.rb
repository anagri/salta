authorization do
  role :guest do
    has_permission_on :users, :to => [:new, :create]
    has_permission_on :user_sessions, :to => [:new, :create]
  end

  role :authenticated_user do
    has_permission_on :user_sessions, :to => [:destroy]
    has_permission_on :dashboard, :to => [:index]
  end

  role :admin do
    includes :authenticated_user
    has_permission_on :groups, :to => [:index, :new, :create]
  end

  role :contact do
    includes :authenticated_user
  end
end
