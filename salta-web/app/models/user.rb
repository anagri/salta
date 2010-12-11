class User < ActiveRecord::Base
  acts_as_authentic

  def roles
    [role.to_sym]
  end
end