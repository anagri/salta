authorization do
  role :guest do
    has_permission_on :users, :to => [:new, :create]
    has_permission_on :user_sessions, :to => [:new, :create]
  end

  role :authenticated_user do
    has_permission_on :user_sessions, :to => [:destroy]
  end

  role :admin do
    includes :authenticated_user
    has_permission_on :groups, :to => [:index, :new, :create, :show,
                                       :add_membership, :remove_membership,
                                       :contacts, :invite]
  end

  role :contact do
    includes :authenticated_user
    has_permission_on :groups, :to => [:index, :show, :invite]
    has_permission_on :groups do
      to :contacts
      if_attribute :contacts => contains {user}
    end
    has_permission_on :invites, :to => [:show]
  end
end
