authorization do
  role :guest do
    has_permission_on :users, :to => [:new, :create]
    has_permission_on :user_sessions, :to => [:new, :create]
  end

  role :authenticated_user do
    has_permission_on :user_sessions, :to => [:destroy]
    has_permission_on :dashboard, :to => [:index]
    has_permission_on :android, :to => [:members, :groups]
    has_permission_on :profiles do
      to :new, :create
      if_attribute :user => is {user}
    end
  end

  role :admin do
    includes :authenticated_user
    has_permission_on :groups, :to => [:index, :new, :create, :show,
                                       :add_membership, :remove_membership,
                                       :members, :invite]
  end

  role :contact do
    includes :authenticated_user
    has_permission_on :groups, :to => [:index, :show, :invite]
    has_permission_on :groups do
      to :members
      if_attribute :members => contains {user.profiles}
    end
    has_permission_on :invites, :to => [:show]
  end
end
